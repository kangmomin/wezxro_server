package com.hwalaon.wezxro_server.domain.service.persistence.adapter

import com.hwalaon.wezxro_server.domain.account.persistence.port.AccountServicePort
import com.hwalaon.wezxro_server.domain.account.persistence.port.dto.ServiceRateInfoDto
import com.hwalaon.wezxro_server.domain.category.persistence.port.CategoryServicePort
import com.hwalaon.wezxro_server.domain.order.persistence.port.OrderServicePort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ServiceAddOrderInfoDto
import com.hwalaon.wezxro_server.domain.provider.persistence.port.ProviderServicePort
import com.hwalaon.wezxro_server.domain.service.exception.ServiceNotFoundException
import com.hwalaon.wezxro_server.domain.service.persistence.customRepository.CustomServiceRepository
import com.hwalaon.wezxro_server.domain.service.persistence.port.ServiceCategoryPort
import com.hwalaon.wezxro_server.domain.service.persistence.port.ServiceProviderPort
import com.hwalaon.wezxro_server.domain.service.persistence.repository.ServiceRepository
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.WapiServicePort
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.dto.WapiServiceDto
import com.hwalaon.wezxro_server.domain.wapi.service.WapiService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.stereotype.Component
import java.util.*

@Component
class ServiceAdapter(
    private val customServiceRepository: CustomServiceRepository,
    private val serviceRepository: ServiceRepository,
    private val serviceProviderPort: ServiceProviderPort,
    private val serviceCategoryPort: ServiceCategoryPort
    ): OrderServicePort, ProviderServicePort, AccountServicePort, CategoryServicePort, WapiServicePort {
    override fun serviceAddOrderInfo(serviceId: Long): ServiceAddOrderInfoDto {
          return customServiceRepository.addOrderServiceInfo(serviceId) ?: throw ServiceNotFoundException()
    }

    override fun deleteByProviderId(providerId: Long, clientId: UUID) {
        serviceRepository.findAllByClientIdAndProviderId(clientId, providerId).forEach { s ->
            s.status = BasicStatus.DELETED
            s.name = "deleted_service_${s.name}"
        }
    }
    override fun deleteByCategoryId(categoryId: Long, clientId: UUID) {
        serviceRepository.findAllByClientIdAndCategoryId(clientId, categoryId).forEach { s ->
            s.status = BasicStatus.DELETED
            s.name = "deleted_service_${s.name}"
        }
    }

    override fun updateStatusByProviderId(providerId: Long, status: BasicStatus, clientId: UUID) {
        val services = serviceRepository.findAllByClientIdAndProviderId(
            providerId = providerId, clientId = clientId)

        if (status == BasicStatus.ACTIVE) {
            val ids = services.map {
                it.categoryId!!
            }

            val deactiveCategory = serviceCategoryPort.categoryByDeactive(ids)
            services.forEach {
                if (it.categoryId !in deactiveCategory) it.status = status
            }
        } else {
            services.forEach {
                it.status = status
            }
        }
    }

    override fun serviceInfo(serviceIds: List<Long>): MutableList<ServiceRateInfoDto> =
        customServiceRepository.serviceInfos(serviceIds)

    override fun updateStatusByCategoryId(categoryId: Long, clientId: UUID, status: BasicStatus) {
        val services = serviceRepository.findAllByClientIdAndCategoryIdAndStatusNot(
            categoryId = categoryId, clientId = clientId)

        if (status == BasicStatus.ACTIVE) {
            val ids = services.map {
                it.providerId!!
            }

            val providerByDeactive = serviceProviderPort.providerByDeactive(ids)
            services.forEach {
                if (it.providerId !in providerByDeactive) it.status = status
            }
        } else {
            services.forEach {
                it.status = status
            }
        }
    }

    override fun getServices(clientId: UUID): MutableList<WapiServiceDto> {
        return serviceRepository.findByClientId(clientId).map {
            WapiServiceDto(
                id = it.id!!,
                name = it.name!!,
                type = it.type!!.toString(),
                min = it.min!!,
                max = it.max!!,
                rate = it.rate!!,
                categoryId = it.categoryId!!,
                refill = it.refill ?: false,
                cancel = it.cancel ?: false
            )
        }.toMutableList()
    }
}