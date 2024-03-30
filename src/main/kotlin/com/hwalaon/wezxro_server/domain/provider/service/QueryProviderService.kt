package com.hwalaon.wezxro_server.domain.provider.service

import com.hwalaon.wezxro_server.domain.provider.controller.response.ProviderServiceListResponse
import com.hwalaon.wezxro_server.domain.provider.exception.ProviderNotFoundException
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.model.ProviderService
import com.hwalaon.wezxro_server.domain.provider.persistence.ProviderPersistence
import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderServiceEntity
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import java.util.*

@ReadOnlyService
class QueryProviderService(
    private val providerPersistence: ProviderPersistence
) {
    fun list(clientId: UUID): List<Provider> {
        return providerPersistence.list(clientId)
    }

    fun adminList(clientId: UUID) =
        providerPersistence.adminList(clientId)

    fun providerServiceCategory(providerId: Long, clientId: UUID): List<ProviderService> =
        providerPersistence.providerDetail(providerId, clientId).let {
            if (it == null) throw ProviderNotFoundException()

            return providerPersistence.providerCategories(it)
        }

    fun providerService(clientId: UUID, providerId: Long): List<ProviderService> {
        providerPersistence.providerDetail(providerId, clientId).let {p ->
            if (p == null) throw ProviderNotFoundException()

            return providerPersistence.providerServices(p.apiUrl!!)
        }
    }
}