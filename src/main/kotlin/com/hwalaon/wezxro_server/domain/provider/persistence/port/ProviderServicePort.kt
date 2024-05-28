package com.hwalaon.wezxro_server.domain.provider.persistence.port

import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface ProviderServicePort {
    fun deleteByProviderId(providerId: Long, clientId: UUID)
}
