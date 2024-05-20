package com.hwalaon.wezxro_server.domain.order.persistence.port

import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import com.hwalaon.wezxro_server.domain.provider.model.Provider
import org.springframework.stereotype.Component

@Component
interface ProviderPort {

    fun providerApiInfo(providerId: Long): ProviderApiDto?
    fun getProvidersById(providerIds: List<Long>): List<Provider>

}