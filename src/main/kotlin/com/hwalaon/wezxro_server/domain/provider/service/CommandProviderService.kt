package com.hwalaon.wezxro_server.domain.provider.service

import com.hwalaon.wezxro_server.domain.provider.exception.ProviderConflictException
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.persistence.ProviderPersistence
import com.hwalaon.wezxro_server.global.annotation.CommandService
import java.util.*

@CommandService
class CommandProviderService(
    private val providerPersistence: ProviderPersistence
) {
    fun addProvider(provider: Provider) {
        if (!providerPersistence.valid(provider)) throw ProviderConflictException()
        val balance = providerPersistence.getBalance(provider).balance
        provider.balance = balance

        providerPersistence.save(provider)
    }

    fun updateStatus(providerId: Long, clientId: UUID) {
        providerPersistence.changeStatus(providerId, clientId)
    }
}