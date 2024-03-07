package com.hwalaon.wezxro_server.domain.deposit.persistence.redis

import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.PendingDepositEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DepositRedisRepository : CrudRepository<PendingDepositEntity, Long> {

    fun existsByNameIsAndAmountIs(name: String, amount: Long): Boolean
}