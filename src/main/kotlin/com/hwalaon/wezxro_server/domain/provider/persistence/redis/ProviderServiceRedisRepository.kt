package com.hwalaon.wezxro_server.domain.provider.persistence.redis

import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderServiceEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProviderServiceRedisRepository: CrudRepository<ProviderServiceEntity, String> {
    fun findByProviderLink(providerLink: String): List<ProviderServiceEntity>
    fun findByServiceAndProviderLink(service: String, providerLink: String): ProviderServiceEntity?
}