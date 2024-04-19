package com.hwalaon.wezxro_server.domain.deposit.persistence.redis

import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.PendingDepositEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DepositRedisRepository : CrudRepository<PendingDepositEntity, Long> {

    fun existsByNameIsAndAmountIs(name: String, amount: Long): Boolean
    fun findByClientId(clientId: UUID): List<PendingDepositEntity>
    fun findByNameAndAmount(name: String, amount: Long): PendingDepositEntity?
}