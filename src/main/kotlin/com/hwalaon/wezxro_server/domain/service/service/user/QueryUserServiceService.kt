package com.hwalaon.wezxro_server.domain.service.service.user

import com.hwalaon.wezxro_server.domain.service.persistence.ServicePersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService

@ReadOnlyService
class QueryUserServiceService(
    private val servicePersistenceAdapter: ServicePersistenceAdapter
) {
    fun serviceDetail(serviceId: Int) =
        servicePersistenceAdapter.serviceDetail(serviceId)

}