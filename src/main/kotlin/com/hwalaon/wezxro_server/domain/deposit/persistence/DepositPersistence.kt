package com.hwalaon.wezxro_server.domain.deposit.persistence

import com.hwalaon.wezxro_server.domain.deposit.controller.response.DepositListResponse
import com.hwalaon.wezxro_server.domain.deposit.mapper.DepositMapper
import com.hwalaon.wezxro_server.domain.deposit.model.Deposit
import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import com.hwalaon.wezxro_server.domain.deposit.persistence.dto.CheckPayDto
import com.hwalaon.wezxro_server.domain.deposit.persistence.dto.DepositInfoDto
import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.DepositEntity
import com.hwalaon.wezxro_server.domain.deposit.persistence.port.AccountDepositPort
import com.hwalaon.wezxro_server.domain.deposit.persistence.redis.DepositRedisRepository
import com.hwalaon.wezxro_server.domain.deposit.persistence.repository.DepositRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class DepositPersistence(
    private val depositRepository: DepositRepository,
    private val depositRedisRepository: DepositRedisRepository,
    private val depositMapper: DepositMapper,
    private val accountPort: AccountDepositPort
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
                depositId = it.id!!,
                amount = it.amount!!,
                name = it.name!!,
                status = DepositType.PENDING,
                note = it.note ?: "",
                updatedAt = LocalDateTime.now()
            )
        }


    fun updateDeposit(payInfo: CheckPayDto): DepositInfoDto? {
        val pendingDeposit = depositRedisRepository.findByNameAndAmount(payInfo.RNAME ?: "", (payInfo.RPAY ?: "0").toLong())
            ?: return null

        val deposit = DepositEntity(
            id = null,
            name = pendingDeposit.name,
            userId = pendingDeposit.userId,
            type = pendingDeposit.type,
            personalPhone = pendingDeposit.personalPhone,
            note = pendingDeposit.note,
            status = DepositType.DONE,
            businessPhone = pendingDeposit.businessPhone,
            businessOwner = pendingDeposit.businessOwner,
            businessName = pendingDeposit.businessName,
            clientId = pendingDeposit.clientId,
            amount = pendingDeposit.amount,
            businessEmail = pendingDeposit.businessEmail,
            businessRegno = pendingDeposit.businessRegno,
        )

        depositRepository.save(deposit)
        depositRedisRepository.delete(pendingDeposit)

        return DepositInfoDto(
            pendingDeposit.userId!!,
            pendingDeposit.amount!!)
    }

    fun pendingListByUserId(userId: Long): MutableList<DepositListResponse> {
        val deposits = depositRedisRepository.findByUserId(userId).map {
            DepositListResponse(
                depositId = it.id!!,
                amount = it.amount!!,
                name = it.name!!,
                status = DepositType.PENDING,
                note = it.note ?: "",
                updatedAt = LocalDateTime.now()
            )
        }.toMutableList()

        val doneDeposits = depositRepository.findByUserIdOrderByUpdatedAtDesc(userId).map {
            DepositListResponse(
                depositId = it.id!!.toString(),
                amount = it.amount!!,
                name = it.name!!,
                status = it.status!!,
                note = it.note ?: "",
                updatedAt = it.updatedAt!!
            )
        }

        deposits.addAll(doneDeposits)

        return deposits
    }

    fun updateMoney(userId: Long, money: Long) = accountPort.updateMoney(userId, money)
}