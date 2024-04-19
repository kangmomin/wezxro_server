package com.hwalaon.wezxro_server.domain.deposit.persistence

import com.google.gson.Gson
import com.hwalaon.wezxro_server.domain.deposit.controller.request.CheckPayRequest
import com.hwalaon.wezxro_server.domain.deposit.controller.response.DepositListResponse
import com.hwalaon.wezxro_server.domain.deposit.mapper.DepositMapper
import com.hwalaon.wezxro_server.domain.deposit.model.Deposit
import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import com.hwalaon.wezxro_server.domain.deposit.persistence.dto.CheckPayDto
import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.DepositEntity
import com.hwalaon.wezxro_server.domain.deposit.persistence.redis.DepositRedisRepository
import com.hwalaon.wezxro_server.domain.deposit.persistence.repository.DepositRepository
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Component
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
                id = it.id!!,
                amount = it.amount!!,
                name = it.name!!,
                type = it.type ?: "default",
                note = it.note ?: ""
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
}