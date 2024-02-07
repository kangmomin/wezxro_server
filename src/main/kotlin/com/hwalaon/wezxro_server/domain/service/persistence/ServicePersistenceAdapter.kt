package com.hwalaon.wezxro_server.domain.service.persistence

import com.hwalaon.wezxro_server.domain.service.mapper.ServiceMapper
import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.service.persistence.entity.ServiceEntity
import com.hwalaon.wezxro_server.domain.service.persistence.repository.ServiceRepository
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*
import javax.management.ServiceNotFoundException

@Component
class ServicePersistenceAdapter(
    private val serviceRepository: ServiceRepository,
    private val serviceMapper: ServiceMapper
) {

    fun serviceDetail(id: Int) =
        serviceRepository.findByIdOrNull(id).let {
            if (it == null) throw ServiceNotFoundException()
            serviceMapper.toDomain(it)
        }


    fun serviceList(clientId: UUID, categoryId: Int): List<Service> {
        val services = if (categoryId == 0) serviceRepository.findAllByClientIdOrderById(clientId)
            else serviceRepository.findAllByClientIdAndCategoryIdOrderById(clientId, categoryId)

        return services.map { serviceEntity ->
            serviceMapper.toDomain(serviceEntity)
        }
    }

    fun save(service: Service) =
        serviceMapper.toEntity(service).let {
            serviceRepository.save(it)
        }

    fun valid(service: Service) =
        serviceRepository.existsByApiServiceIdAndProviderIdOrName(
            service.apiServiceId!!, service.providerId!!, service.name!!)

    fun delete(id: Int) =
        serviceRepository.findByIdOrNull(id).let {
            if (it == null) throw ServiceNotFoundException()
            it.status = BasicStatus.DELETED
        }
}