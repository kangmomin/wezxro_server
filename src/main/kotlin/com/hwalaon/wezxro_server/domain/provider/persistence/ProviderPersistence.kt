package com.hwalaon.wezxro_server.domain.provider.persistence

import com.hwalaon.wezxro_server.domain.provider.exception.ProviderNotFoundException
import com.hwalaon.wezxro_server.domain.provider.mapper.ProviderMapper
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.persistence.customRepository.CustomProviderRepository
import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderServiceEntity
import com.hwalaon.wezxro_server.domain.provider.persistence.repository.ProviderRepository
import com.hwalaon.wezxro_server.domain.provider.persistence.redis.ProviderServiceRedisRepository
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

    fun syncServices(provider: Provider) {
        val apiProvider = ApiProvider(provider.apiKey!!, provider.apiUrl!!)

        val servicesDto = apiProvider.getServices().toMutableList()
        val removeServiceIds: MutableList<String> = ArrayList()

        providerServiceRedisRepository.findAll().forEach {pse ->
            if (pse.providerLink !== provider.apiUrl) return

            servicesDto.forEach {psd ->
                if (pse.service == psd.service) {
                    pse.name = psd.name
                    pse.category = psd.category
                    pse.max = psd.max
                    pse.min = psd.min
                    pse.rate = psd.rate
                    pse.type = psd.type
                    pse.cancel = psd.cancel
                    pse.dripfeed = psd.dripfeed
                    pse.refill = psd.refill
                }

                servicesDto.remove(psd)
                removeServiceIds.add(psd.service)
            }
        }

        val servicesEntity = servicesDto.map {
            ProviderServiceEntity(
                id = null,
                rate = it.rate,
                max = it.max,
                min = it.min,
                type = it.type,
                name = it.name,
                category = it.category,
                service = it.service,
                providerLink = provider.apiUrl,
                cancel = it.cancel,
                refill = it.refill,
                dripfeed = it.dripfeed
            )
        }

        providerServiceRedisRepository.removeAllByProviderLinkAndServiceNotIn(provider.apiUrl, removeServiceIds)
        providerServiceRedisRepository.saveAll(servicesEntity)
    }

    fun providerServices(apiUrl: String) =
        providerServiceRedisRepository.findByProviderLink(apiUrl)
}