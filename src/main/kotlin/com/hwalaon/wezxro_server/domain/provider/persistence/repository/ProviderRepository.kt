package com.hwalaon.wezxro_server.domain.provider.persistence.repository

import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProviderRepository: JpaRepository<ProviderEntity, Long> {
    fun existsByClientIdAndNameAndStatusIsNotAndIdIsNotOrApiUrlAndApiKeyAndStatusIsNotAndIdIsNot(
        clientId: UUID,
        name: String,
        status: BasicStatus = BasicStatus.DELETED,
        id: Long = 0,
        apiUrl: String,
        apiKey: String,
        status2: BasicStatus = BasicStatus.DELETED,
        id2: Long = 0
    ): Boolean
    fun findAllByClientIdAndStatus(clientId: UUID, status: BasicStatus = BasicStatus.ACTIVE): List<ProviderEntity>
    fun findAllByClientIdAndStatusIsNotOrderById(clientId: UUID, status: BasicStatus = BasicStatus.DELETED): List<ProviderEntity>
    fun findByClientIdAndIdAndStatusIsNot(clientId: UUID, id: Long, status: BasicStatus = BasicStatus.DELETED): ProviderEntity?

    fun findIdAndApiKeyAndApiUrlByIdIn(id: List<Long>): List<ProviderEntity>
}