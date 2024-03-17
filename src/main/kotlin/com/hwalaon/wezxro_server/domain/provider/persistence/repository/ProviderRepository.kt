package com.hwalaon.wezxro_server.domain.provider.persistence.repository

import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProviderRepository: JpaRepository<ProviderEntity, Long> {
    fun existsByClientIdIsAndNameOrApiUrlIsAndApiKeyIs(clientId: UUID, name: String, apiUrl: String, apiKey: String): Boolean
    fun findAllByClientId(clientId: UUID): List<ProviderEntity>
    fun findByClientIdAndId(clientId: UUID, id: Long): ProviderEntity?
}