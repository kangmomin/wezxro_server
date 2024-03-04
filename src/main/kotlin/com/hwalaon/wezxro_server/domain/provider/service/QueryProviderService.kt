package com.hwalaon.wezxro_server.domain.provider.service

import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.persistence.ProviderPersistence
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import java.util.*

@ReadOnlyService
class QueryProviderService(
    private val providerPersistence: ProviderPersistence
) {
    fun list(clientId: UUID): List<Provider> {
        return providerPersistence.list(clientId)
    }
}