package com.hwalaon.wezxro_server.domain.provider.service

import com.hwalaon.wezxro_server.domain.provider.exception.ProviderNotFoundException
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.model.ProviderService
import com.hwalaon.wezxro_server.domain.provider.persistence.ProviderPersistence
import com.hwalaon.wezxro_server.domain.service.exception.ServiceNotFoundException
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

    fun providerServiceCategory(providerId: Long, clientId: UUID): List<String?> =
        providerPersistence.providerDetail(providerId, clientId).let {p ->
            if (p == null) throw ProviderNotFoundException()

            return providerPersistence.providerCategories(p).map {
                it.category
            }
        }

    fun providerService(clientId: UUID, providerId: Long, category: String?): List<ProviderService> {
        providerPersistence.providerDetail(providerId, clientId).let {p ->
            if (p == null) throw ProviderNotFoundException()

            return if (category.isNullOrBlank()) providerPersistence.providerServices(p.apiUrl!!)
                else providerPersistence.providerServicesByCategory(p.apiUrl!!, category)
        }
    }

    fun searchProviderService(serviceId: String, providerId: Long, clientId: UUID): ProviderService {
        val provider = providerPersistence.providerDetail(providerId, clientId) ?: throw ProviderNotFoundException()

        return providerPersistence.searchProviderService(serviceId, provider.apiUrl!!) ?: throw ServiceNotFoundException()
    }

    fun providerDetail(providerId: Long, clientId: UUID): Provider {
        return providerPersistence.providerDetail(providerId, clientId) ?: throw ProviderNotFoundException()
    }
}