package com.hwalaon.wezxro_server.domain.provider.persistence.adpater

import com.hwalaon.wezxro_server.domain.order.persistence.port.ProviderPort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import com.hwalaon.wezxro_server.domain.provider.exception.ProviderNotFoundException
import com.hwalaon.wezxro_server.domain.provider.persistence.customRepository.CustomProviderRepository
import org.springframework.stereotype.Component

@Component
class OrderAdapter(
    private val customProviderRepository: CustomProviderRepository
): ProviderPort {
    override fun providerApiInfo(providerId: Long): ProviderApiDto {
        return customProviderRepository.getApiInfo(providerId) ?: throw ProviderNotFoundException()
    }
}