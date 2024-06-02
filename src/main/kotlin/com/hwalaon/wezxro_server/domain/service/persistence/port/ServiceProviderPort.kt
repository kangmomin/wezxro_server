package com.hwalaon.wezxro_server.domain.service.persistence.port

import org.springframework.stereotype.Component

@Component
interface ServiceProviderPort {

    /**
     * returns deactive provider ids
     */
    fun providerByDeactive(providerIds: List<Long>): List<Long>
}
