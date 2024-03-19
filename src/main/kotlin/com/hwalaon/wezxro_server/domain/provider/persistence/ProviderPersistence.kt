package com.hwalaon.wezxro_server.domain.provider.persistence

import com.hwalaon.wezxro_server.domain.provider.exception.ProviderNotFoundException
import com.hwalaon.wezxro_server.domain.provider.mapper.ProviderMapper
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.persistence.customRepository.CustomProviderRepository
import com.hwalaon.wezxro_server.domain.provider.persistence.repository.ProviderRepository
import com.hwalaon.wezxro_server.domain.provider.persistence.repository.ProviderServiceRedisRepository
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.util.ApiProvider
import com.hwalaon.wezxro_server.global.common.util.dto.UserBalanceDto
import org.springframework.stereotype.Component
import java.util.*

@Component
class ProviderPersistence(
    private val providerRepository: ProviderRepository,
    private val customProviderRepository: CustomProviderRepository,
    private val providerServiceRedisRepository: ProviderServiceRedisRepository,
    private val providerMapper: ProviderMapper
) {
    fun valid(provider: Provider) =
        !providerRepository.existsByClientIdAndNameAndStatusIsNotOrApiUrlAndApiKeyAndStatusIsNot(
            clientId = provider.clientId!!,
            name = provider.name!!,
            apiUrl = provider.apiUrl!!,
            apiKey = provider.apiKey!!
        )

    fun save(provider: Provider) =
        providerRepository.save(
            providerMapper.toEntity(provider)).id

    fun getBalance(provider: Provider): UserBalanceDto {
        val api = ApiProvider(apiUrl = provider.apiUrl!!, apiKey = provider.apiKey!!)
        return api.getUserBalance()
    }

    fun list(clientId: UUID) =
        providerRepository.findAllByClientIdAndStatus(clientId).map {
            providerMapper.toDomain(it)
        }

    fun changeStatus(providerId: Long, clientId: UUID) {
        val provider = providerRepository.findByClientIdAndId(clientId, providerId) ?: throw ProviderNotFoundException()

        if (provider.status!! == BasicStatus.ACTIVE) provider.status = BasicStatus.DEACTIVE
        else if (provider.status!! == BasicStatus.DEACTIVE) provider.status = BasicStatus.ACTIVE
    }

    fun adminList(clientId: UUID) =
        providerRepository.findAllByClientIdAndStatusIsNot(clientId).map {
            providerMapper.toDomain(it)
        }

    fun providerCategories(provider: Provider): List<String> {
        val categories = providerServiceRedisRepository.findByProviderLink(provider.apiUrl!!).map {
            it.category
        }
        return categories
    }

    fun providerDetail(providerId: Long, clientId: UUID): Provider? =
        providerRepository.findByClientIdAndId(clientId, providerId).let {
            if (it == null) return null
            providerMapper.toDomain(it)
        }
}