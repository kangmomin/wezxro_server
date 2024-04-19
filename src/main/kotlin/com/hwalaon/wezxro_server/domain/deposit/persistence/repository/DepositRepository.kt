package com.hwalaon.wezxro_server.domain.deposit.persistence.repository

import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.DepositEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DepositRepository: JpaRepository<DepositEntity, Long> {

    fun findByUserIdOrderByUpdatedAt(userId: Long): List<DepositEntity>
}
