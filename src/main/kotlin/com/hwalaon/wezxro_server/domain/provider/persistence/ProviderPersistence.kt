package com.hwalaon.wezxro_server.domain.provider.persistence

import com.hwalaon.wezxro_server.domain.provider.persistence.customRepository.CustomProviderRepository
import com.hwalaon.wezxro_server.domain.provider.persistence.repository.ProviderRepository
import org.springframework.stereotype.Component

@Component
class ProviderPersistence(
    private val providerRepository: ProviderRepository,
    private val customProviderRepository: CustomProviderRepository
) {
}