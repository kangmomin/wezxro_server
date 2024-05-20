package com.hwalaon.wezxro_server.domain.provider.persistence.adpater

import com.hwalaon.wezxro_server.domain.order.persistence.port.ProviderPort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import com.hwalaon.wezxro_server.domain.provider.exception.ProviderNotFoundException
import com.hwalaon.wezxro_server.domain.provider.mapper.ProviderMapper
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.persistence.customRepository.CustomProviderRepository
import com.hwalaon.wezxro_server.domain.provider.persistence.repository.ProviderRepository
import org.springframework.stereotype.Component

@Component
class ProviderAdapter(
    private val customProviderRepository: CustomProviderRepository,
    private val providerRepository: ProviderRepository,
    private val providerMapper: ProviderMapper
): ProviderPort {

    override fun providerApiInfo(providerId: Long): ProviderApiDto? =
        customProviderRepository.getApiInfo(providerId)

    override fun getProvidersById(providerIds: List<Long>): List<Provider> {
        return providerRepository.findIdAndApiKeyAndApiUrlByIdIn(providerIds).map {
            providerMapper.toDomain(it)
        }
    }
}