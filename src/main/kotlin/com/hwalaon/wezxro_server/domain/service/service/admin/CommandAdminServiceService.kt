package com.hwalaon.wezxro_server.domain.service.service.admin

import com.hwalaon.wezxro_server.domain.service.controller.request.StoreServiceRequest
import com.hwalaon.wezxro_server.domain.service.exception.ProviderOrCategoryDeactiveException
import com.hwalaon.wezxro_server.domain.service.exception.ServiceConflictException
import com.hwalaon.wezxro_server.domain.service.exception.ServiceNotFoundException
import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.service.persistence.ServicePersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.CommandService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import java.util.*

@CommandService
class CommandAdminServiceService(
    private val servicePersistenceAdapter: ServicePersistenceAdapter
) {
    fun add(service: Service) {
        if (!servicePersistenceAdapter.valid(service))
            throw ServiceConflictException()
        servicePersistenceAdapter.save(service)
    }

    fun delete(id: Int) =
        servicePersistenceAdapter.delete(id)

    fun updateStatus(serviceId: Long, clientId: UUID): BasicStatus {
        val status = servicePersistenceAdapter.toggleStatus(serviceId, clientId)
            ?: throw ServiceNotFoundException()

        return status
    }

    fun update(storeServiceRequest: StoreServiceRequest, serviceId:Long, clientId: UUID) {
        val service = storeServiceRequest.toDomain(clientId)
        service.id = serviceId

        if (!servicePersistenceAdapter.valid(service)) throw ServiceConflictException()

        if (service.status!! == BasicStatus.ACTIVE &&
            !servicePersistenceAdapter.validStatus(service.categoryId!!, service.providerId!!))
            throw ProviderOrCategoryDeactiveException()

        servicePersistenceAdapter.update(service) ?: throw ServiceNotFoundException()
    }

    fun deleteCustomRate(serviceId: Long) =
        servicePersistenceAdapter.deleteCustomRate(serviceId)

}