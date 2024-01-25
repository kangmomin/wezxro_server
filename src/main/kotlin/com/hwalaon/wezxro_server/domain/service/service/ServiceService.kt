package com.hwalaon.wezxro_server.domain.service.service

import com.hwalaon.wezxro_server.domain.service.controller.response.ServiceDetailResponse
import com.hwalaon.wezxro_server.domain.service.persistence.ServicePersistenceAdapter
import org.springframework.stereotype.Service

@Service
class ServiceService(
    private val servicePersistenceAdapter: ServicePersistenceAdapter
) {

    fun serviceDetail(serviceId: Int) =
        servicePersistenceAdapter.serviceDetail(serviceId).let {
            ServiceDetailResponse.fromDomain(it)
        }

}