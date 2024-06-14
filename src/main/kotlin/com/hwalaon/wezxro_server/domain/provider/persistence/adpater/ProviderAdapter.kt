package com.hwalaon.wezxro_server.domain.provider.persistence.adpater

import com.hwalaon.wezxro_server.domain.order.persistence.port.ProviderPort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import com.hwalaon.wezxro_server.domain.provider.mapper.ProviderMapper
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.persistence.customRepository.CustomProviderRepository
import com.hwalaon.wezxro_server.domain.provider.persistence.repository.ProviderRepository
import com.hwalaon.wezxro_server.domain.service.persistence.port.ServiceProviderPort
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.WapiProviderPort
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProviderAdapter(
    private val customProviderRepository: CustomProviderRepository,
    private val providerRepository: ProviderRepository,
    private val providerMapper: ProviderMapper
): ProviderPort, ServiceProviderPort, WapiProviderPort {

    override fun providerApiInfo(providerId: Long): ProviderApiDto? =
        customProviderRepository.getApiInfo(providerId)

    override fun getProvidersById(providerIds: List<Long>): List<Provider> {
        return providerRepository.findIdAndApiKeyAndApiUrlByIdIn(providerIds).map {
            providerMapper.toDomain(it)
        }
    }

    override fun providerByDeactive(providerIds: List<Long>): List<Long> =
        providerRepository.findAllByIdInAndStatus(providerIds).map {
            it.id!!
        }

    override fun isProviderDeactive(providerId: Long): Boolean =
        (providerRepository.findByIdOrNull(providerId)?.status ?: BasicStatus.DEACTIVE) == BasicStatus.DEACTIVE

    override fun getProviderInfoById(id: Long): ProviderApiDto? =
        customProviderRepository.getApiInfo(id)
}