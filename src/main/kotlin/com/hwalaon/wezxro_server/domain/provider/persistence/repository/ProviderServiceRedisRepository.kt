package com.hwalaon.wezxro_server.domain.provider.persistence.repository

import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderServiceEntity
import org.springframework.data.repository.CrudRepository

interface ProviderServiceRedisRepository: CrudRepository<ProviderServiceEntity, String> {
    fun findByProviderLink(providerLink: String): List<ProviderServiceEntity>
}