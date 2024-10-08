package com.hwalaon.wezxro_server.domain.deposit.persistence.repository

import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.DepositEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DepositRepository: JpaRepository<DepositEntity, Long> {

    fun findByUserIdAndStatusNotOrderByUpdatedAtDesc(userId: Long, status: DepositType = DepositType.DELETE): List<DepositEntity>
    fun findByIdAndClientId(depositId: Long?, clientId: UUID): DepositEntity?
}
