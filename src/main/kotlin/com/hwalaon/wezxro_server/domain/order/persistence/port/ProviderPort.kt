package com.hwalaon.wezxro_server.domain.order.persistence.port

import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import org.springframework.stereotype.Component

@Component
interface ProviderPort {

    fun providerApiInfo(apiServiceId: Long): ProviderApiDto
}