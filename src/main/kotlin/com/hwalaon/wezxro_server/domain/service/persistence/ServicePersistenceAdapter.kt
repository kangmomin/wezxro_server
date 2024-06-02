package com.hwalaon.wezxro_server.domain.service.persistence

import com.hwalaon.wezxro_server.domain.service.exception.ProviderOrCategoryDeactiveException
import com.hwalaon.wezxro_server.domain.service.mapper.ServiceMapper
import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.service.persistence.customRepository.CustomServiceRepository
import com.hwalaon.wezxro_server.domain.service.persistence.port.ServiceCategoryPort
import com.hwalaon.wezxro_server.domain.service.persistence.port.ServiceProviderPort
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
    private val serviceMapper: ServiceMapper,
    private val serviceProviderPort: ServiceProviderPort,
    private val serviceCategoryPort: ServiceCategoryPort
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
            service.apiServiceId!!, service.providerId!!, service.name!!, service.id)!! == 0L

    fun delete(id: Int) =
        serviceRepository.findByIdOrNull(id).let {
            if (it == null) throw ServiceNotFoundException()
            it.status = BasicStatus.DELETED
            it.name = "deleted_service_${it.name}"
        }

    fun userServiceDetailList(userId: Long?, clientId: UUID?, category: Long?) =
        customServiceRepository.serviceDetailList(userId, clientId, category)

    fun toggleStatus(serviceId: Long, clientId: UUID): BasicStatus? {
        val service = serviceRepository.findByClientIdAndId(clientId, serviceId) ?: return null

        // 이 부분은 service 영역인데 service로 옮기기엔 너무 헤비해서 일단 여따 박아 넣음
        // ---------- service 영역 -------------
        val isProviderDeactive = serviceProviderPort.isProviderDeactive(service.providerId!!)
        val isCategoryDeactive = serviceCategoryPort.isCategoryDeactive(service.categoryId!!)

        if (service.status!! == BasicStatus.DEACTIVE &&
            (isProviderDeactive || isCategoryDeactive))
            throw ProviderOrCategoryDeactiveException()
        // ---------- service 영역 -------------

        service.status = if (service.status!! == BasicStatus.ACTIVE) BasicStatus.DEACTIVE else BasicStatus.ACTIVE

        return service.status
    }

    fun update(service: Service): String? {
        val serviceEntity = serviceRepository.findByClientIdAndId(service.clientId!!, service.id!!) ?: return null

        serviceEntity.apiServiceId = service.apiServiceId
        serviceEntity.providerId = service.providerId
        serviceEntity.categoryId = service.categoryId
        serviceEntity.max = service.max
        serviceEntity.min = service.min
        serviceEntity.description = service.description
        serviceEntity.name = service.name
        serviceEntity.originalRate = service.originalRate
        serviceEntity.rate = service.rate
        serviceEntity.status = service.status
        serviceEntity.type = service.type

        return ""
    }

    fun validStatus(categoryId: Long, providerId: Long): Boolean {
        val isProviderDeactive = serviceProviderPort.isProviderDeactive(providerId)
        val isCategoryDeactive = serviceCategoryPort.isCategoryDeactive(categoryId)

        return !(isProviderDeactive || isCategoryDeactive)
    }
}