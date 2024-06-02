package com.hwalaon.wezxro_server.domain.provider.persistence.port

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface ProviderServicePort {
    fun deleteByProviderId(providerId: Long, clientId: UUID)
    fun updateStatusByProviderId(providerId: Long, status: BasicStatus, clientId: UUID)
}
