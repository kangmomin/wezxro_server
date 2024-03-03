package com.hwalaon.wezxro_server.domain.service.persistence

import com.hwalaon.wezxro_server.domain.service.mapper.ServiceMapper
import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.service.persistence.customRepository.CustomServiceRepository
import com.hwalaon.wezxro_server.domain.service.persistence.repository.ServiceRepository
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*
import javax.management.ServiceNotFoundException

@Component
class ServicePersistenceAdapter(
    private val serviceRepository: ServiceRepository,
    private val customServiceRepository: CustomServiceRepository,
    private val serviceMapper: ServiceMapper
) {

    fun userServiceDetail(serviceId: Long, userId: Long, clientId: UUID) =
        customServiceRepository.serviceDetail(userId, serviceId, clientId)


    fun serviceList(clientId: UUID, categoryId: Long): List<Service> {
        val services = if (categoryId == 0L) serviceRepository.findAllByClientIdAndStatusNotOrderById(clientId)
            else serviceRepository.findAllByClientIdAndCategoryIdAndStatusNotOrderById(clientId, categoryId)

        return services.map { serviceEntity ->
            serviceMapper.toDomain(serviceEntity)
        }
    }

    fun save(service: Service) =
        serviceMapper.toEntity(service).let {
            serviceRepository.save(it)
        }

    fun valid(service: Service) =
        customServiceRepository.validService(
            service.apiServiceId!!, service.providerId!!, service.name!!)!! == 0L

    fun delete(id: Int) =
        serviceRepository.findByIdOrNull(id).let {
            if (it == null) throw ServiceNotFoundException()
            it.status = BasicStatus.DELETED
        }

    fun userServiceDetailList(userId: Long?, clientId: UUID?, category: String?) =
        customServiceRepository.serviceDetailList(userId, clientId, category)
}