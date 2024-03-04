package com.hwalaon.wezxro_server.domain.service.service.admin

import com.hwalaon.wezxro_server.domain.service.controller.response.ServiceListResponse
import com.hwalaon.wezxro_server.domain.service.persistence.ServicePersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import java.util.*

@ReadOnlyService
class QueryAdminServiceService(
    private val servicePersistenceAdapter: ServicePersistenceAdapter
) {
    fun serviceList(clientId: UUID?, categoryId: Long) =
        servicePersistenceAdapter.serviceList(clientId!!, categoryId).map {
            ServiceListResponse(it.id!!, it.name!!)
        }


}