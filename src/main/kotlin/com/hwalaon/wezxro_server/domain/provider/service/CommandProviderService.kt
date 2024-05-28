package com.hwalaon.wezxro_server.domain.provider.service

import com.hwalaon.wezxro_server.domain.provider.exception.ProviderConflictException
import com.hwalaon.wezxro_server.domain.provider.exception.ProviderNotFoundException
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.persistence.ProviderPersistence
import com.hwalaon.wezxro_server.global.annotation.CommandService
import com.hwalaon.wezxro_server.global.common.exception.ApiRequestFailedException
import java.util.*

@CommandService
class CommandProviderService(
    private val providerPersistence: ProviderPersistence
) {
    fun addProvider(provider: Provider) {
        if (!providerPersistence.valid(provider)) throw ProviderConflictException()
        val balance = providerPersistence.getBalance(provider).balance
        provider.balance = balance.toDouble()

        providerPersistence.save(provider)
    }

    fun updateStatus(providerId: Long, clientId: UUID) {
        providerPersistence.changeStatus(providerId, clientId)
    }

    fun syncProviderServices(providerId: Long, clientId: UUID) {
        val providerDetail = providerPersistence.providerDetail(providerId, clientId) ?: throw ProviderNotFoundException()

        providerPersistence.syncServices(providerDetail)
    }

    fun updateProvider(provider: Provider) {
        if (!providerPersistence.valid(provider)) throw ProviderConflictException()

        providerPersistence.update(provider) ?: throw ProviderNotFoundException()
    }

    fun syncBalance(providerId: Long, clientId: UUID) {
        val s = providerPersistence.syncBalance(providerId, clientId) ?: throw ProviderNotFoundException()

        if (s == "balance exception") throw ApiRequestFailedException()
    }

    fun delete(providerId: Long, clientId: UUID) {
        providerPersistence.delete(providerId, clientId) ?: throw ProviderNotFoundException()
    }
}