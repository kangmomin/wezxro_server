package com.hwalaon.wezxro_server.domain.order.persistence.port

import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProvideApiDto
import org.springframework.stereotype.Component

@Component
interface ProviderPort {

    fun providerApiInfo(serviceId: Long): ProvideApiDto
}