package com.hwalaon.wezxro_server.domain.provider.persistence.repository

import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProviderRepository: JpaRepository<ProviderEntity, Long> {
    fun existsByClientIdAndNameAndStatusIsNotOrApiUrlAndApiKeyAndStatusIsNot(
        clientId: UUID,
        name: String,
        status: BasicStatus = BasicStatus.DELETED,
        apiUrl: String,
        apiKey: String,
        status2: BasicStatus = BasicStatus.DELETED
    ): Boolean
    fun findAllByClientIdAndStatus(clientId: UUID, status: BasicStatus = BasicStatus.ACTIVE): List<ProviderEntity>
    fun findAllByClientIdAndStatusIsNot(clientId: UUID, status: BasicStatus = BasicStatus.DELETED): List<ProviderEntity>
    fun findByClientIdAndId(clientId: UUID, id: Long): ProviderEntity?

    fun findIdAndApiKeyAndApiUrlByIdIn(id: List<Long>): List<ProviderEntity>
}