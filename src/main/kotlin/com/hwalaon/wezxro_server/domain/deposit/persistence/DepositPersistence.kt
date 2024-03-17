package com.hwalaon.wezxro_server.domain.deposit.persistence

import com.hwalaon.wezxro_server.domain.deposit.controller.response.DepositListResponse
import com.hwalaon.wezxro_server.domain.deposit.mapper.DepositMapper
import com.hwalaon.wezxro_server.domain.deposit.model.Deposit
import com.hwalaon.wezxro_server.domain.deposit.persistence.redis.DepositRedisRepository
import com.hwalaon.wezxro_server.domain.deposit.persistence.repository.DepositRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class DepositPersistence(
    private val depositRepository: DepositRepository,
    private val depositRedisRepository: DepositRedisRepository,
    private val depositMapper: DepositMapper
) {

    fun save(deposit: Deposit) {
        depositMapper.toRedisEntity(deposit).let {
            depositRedisRepository.save(it)
        }
    }

    fun conflictValid(deposit: Deposit): Boolean =
        depositRedisRepository.existsByNameIsAndAmountIs(deposit.name!!, deposit.amount!!)

    fun pendingList(clientId: UUID) =
        depositRedisRepository.findByClientId(clientId).map {
            DepositListResponse(
                id = it.id!!,
                amount = it.amount!!,
                name = it.name!!,
                type = it.type ?: "default",
                note = it.note ?: ""
            )
        }
}