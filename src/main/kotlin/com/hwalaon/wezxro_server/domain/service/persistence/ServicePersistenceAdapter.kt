package com.hwalaon.wezxro_server.domain.service.persistence

import com.hwalaon.wezxro_server.domain.service.mapper.ServiceMapper
import com.hwalaon.wezxro_server.domain.service.persistence.repository.ServiceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
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

}