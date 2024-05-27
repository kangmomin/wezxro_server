package com.hwalaon.wezxro_server.domain.provider.persistence

import com.hwalaon.wezxro_server.domain.provider.exception.ProviderNotFoundException
import com.hwalaon.wezxro_server.domain.provider.mapper.ProviderMapper
import com.hwalaon.wezxro_server.domain.provider.mapper.ProviderServiceMapper
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.model.ProviderService
import com.hwalaon.wezxro_server.domain.provider.persistence.customRepository.CustomProviderRepository
import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderServiceEntity
import com.hwalaon.wezxro_server.domain.provider.persistence.redis.ProviderServiceRedisRepository
import com.hwalaon.wezxro_server.domain.provider.persistence.repository.ProviderRepository
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.util.ApiProvider
import com.hwalaon.wezxro_server.global.common.util.response.UserBalanceDto
import org.springframework.stereotype.Component
import java.util.*

@Component
class ProviderPersistence(
    private val providerRepository: ProviderRepository,
    private val customProviderRepository: CustomProviderRepository,
    private val providerServiceRedisRepository: ProviderServiceRedisRepository,
    private val providerMapper: ProviderMapper,
    private val providerServiceMapper: ProviderServiceMapper
) {
    fun valid(provider: Provider) =
        !providerRepository.existsByClientIdAndNameAndStatusIsNotAndIdIsNotOrApiUrlAndApiKeyAndStatusIsNotAndIdIsNot(
            clientId = provider.clientId!!,
            name = provider.name!!,
            apiUrl = provider.apiUrl!!,
            apiKey = provider.apiKey!!,
            id = provider.id ?: 0,
            id2 = provider.id ?: 0
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
        val provider = providerRepository.findByClientIdAndIdAndStatusIsNot(clientId, providerId) ?: throw ProviderNotFoundException()

        if (provider.status!! == BasicStatus.ACTIVE) provider.status = BasicStatus.DEACTIVE
        else if (provider.status!! == BasicStatus.DEACTIVE) provider.status = BasicStatus.ACTIVE
    }

    fun adminList(clientId: UUID) =
        providerRepository.findAllByClientIdAndStatusIsNot(clientId).map {
            providerMapper.toDomain(it)
        }

    fun providerCategories(provider: Provider) =
         providerServiceRedisRepository.findByProviderLink(provider.apiUrl!!).map {
             providerServiceMapper.toDomain(it)
         }

    fun providerDetail(providerId: Long, clientId: UUID): Provider? =
        providerRepository.findByClientIdAndIdAndStatusIsNot(clientId, providerId).let {
            if (it == null) return null
            providerMapper.toDomain(it)
        }

    fun syncServices(provider: Provider) {
        val apiProvider = ApiProvider(provider.apiKey!!, provider.apiUrl!!)

        val servicesDto = apiProvider.getServices().toMutableList()

        val providerServiceEntityList = providerServiceRedisRepository.findByProviderLink(provider.apiUrl).toMutableList()
        providerServiceEntityList.forEach { pse ->
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

                // 사라진 서비스를 삭제하기 업데이트 되는 서비스 배열에서 제외
                providerServiceEntityList.remove(pse)

                // 새로운 서비스를 저장을 위해 업데이트되는 서비스는 제외
                servicesDto.remove(psd)
            }
        }

        val newServiceEntities = servicesDto.map {
            ProviderServiceEntity(
                id = null,
                service = it.service,
                type = it.type,
                category = it.category,
                providerLink = provider.apiUrl,
                max = it.max,
                min = it.min,
                refill = it.refill,
                rate = it.rate,
                cancel = it.cancel,
                dripfeed = it.dripfeed,
                name = it.name
            )
        }

        // 사라진 서비스들은 삭제 처리
        providerServiceRedisRepository.deleteAll(providerServiceEntityList)
        
        providerServiceRedisRepository.saveAll(newServiceEntities)
    }

    fun providerServices(apiUrl: String) =
        providerServiceRedisRepository.findByProviderLink(apiUrl).map {
            providerServiceMapper.toDomain(it)
        }
    fun providerServicesByCategory(apiUrl: String, category: String) =
        providerServiceRedisRepository.findByCategoryAndProviderLink(category, apiUrl).map {
            providerServiceMapper.toDomain(it)
        }

    fun searchProviderService(serviceId: String, apiUrl: String): ProviderService? {
        providerServiceRedisRepository.findByServiceAndProviderLink(serviceId, apiUrl).let {
            if (it == null) return null
            return providerServiceMapper.toDomain(it)
        }
    }

    fun update(providerRequest: Provider): String? {
        val provider =
            providerRepository.findByClientIdAndIdAndStatusIsNot(providerRequest.clientId!!, providerRequest.id!!) ?: return null

        provider.name = providerRequest.name
        provider.apiUrl = providerRequest.apiUrl
        provider.apiKey = providerRequest.apiKey
        provider.description = providerRequest.description
        provider.status = providerRequest.status

        return ""
    }
}