package com.hwalaon.wezxro_server.domain.provider.persistence

import com.hwalaon.wezxro_server.domain.provider.mapper.ProviderMapper
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.persistence.customRepository.CustomProviderRepository
import com.hwalaon.wezxro_server.domain.provider.persistence.repository.ProviderRepository
import com.hwalaon.wezxro_server.global.common.util.ApiProvider
import com.hwalaon.wezxro_server.global.common.util.dto.UserBalanceDto
import org.springframework.stereotype.Component
import java.util.*

@Component
class ProviderPersistence(
    private val providerRepository: ProviderRepository,
    private val customProviderRepository: CustomProviderRepository,
    private val providerMapper: ProviderMapper
) {
    fun valid(provider: Provider) =
        !providerRepository.existsByClientIdIsAndNameOrApiUrlIsAndApiKeyIs(
            provider.clientId!!,
            provider.name!!,
            provider.apiUrl!!,
            provider.apiKey!!
        )

    fun save(provider: Provider) =
        providerRepository.save(
            providerMapper.toEntity(provider)).id

    fun getBalance(provider: Provider): UserBalanceDto {
        val api = ApiProvider(apiUrl = provider.apiUrl!!, apiKey = provider.apiKey!!)
        return api.getUserBalance()
    }

    fun list(clientId: UUID) =
        providerRepository.findAllByClientId(clientId).map {
            providerMapper.toDomain(it)
        }
}