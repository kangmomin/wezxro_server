package com.hwalaon.wezxro_server.domain.service.service.admin

import com.hwalaon.wezxro_server.domain.service.exception.ServiceConflictException
import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.service.persistence.ServicePersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.CommandService

@CommandService
class CommandAdminServiceService(
    private val servicePersistenceAdapter: ServicePersistenceAdapter
) {
    fun add(service: Service) {
        if (!servicePersistenceAdapter.valid(service))
            throw ServiceConflictException()
        servicePersistenceAdapter.save(service)
    }
}