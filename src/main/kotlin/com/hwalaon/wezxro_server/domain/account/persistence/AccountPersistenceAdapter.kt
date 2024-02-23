package com.hwalaon.wezxro_server.domain.account.persistence

import com.hwalaon.wezxro_server.domain.account.controller.request.AddCustomRateRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.persistence.mapper.AccountMapper
import com.hwalaon.wezxro_server.domain.account.persistence.repository.AccountEntityRepository
import com.hwalaon.wezxro_server.domain.account.persistence.repository.CustomRateRepository
import com.hwalaon.wezxro_server.domain.account.persistence.repository.detailQuery.ValidAccountRepository
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountPersistenceAdapter(
    private val accountEntityRepository: AccountEntityRepository,
    private val accountMapper: AccountMapper,
    private val validAccountRepository: ValidAccountRepository,
    private val customRateRepository: CustomRateRepository,
) {
    fun login(loginRequest: LoginRequest) =
        accountEntityRepository.findOneByEmailAndClientId(
            loginRequest.email,
            loginRequest.key
        ).let {
            if (it == null) throw AccountNotFoundException()
            accountMapper.toDomain(it)
        }

    fun join(account: Account) =
        accountMapper.toEntity(account).let {
            accountEntityRepository.save(it)
        }

    fun isExistAccount(email: String, clientId: UUID) = validAccountRepository.isExistEmail(email, clientId)
    fun isExistName(name: String, clientId: UUID) = validAccountRepository.isExistName(name, clientId)
    fun updateInfo(account: Account) =
        accountEntityRepository.findById(account.userId!!).let {
            if (it.isEmpty) throw AccountNotFoundException()

            val accountEntity = it.get()
            accountEntity.email = account.email
            accountEntity.name = account.name
            accountEntity.password = account.password
            accountEntity.money = account.money
            accountEntity.status = account.status
        }

    fun findById(id: Int, clientId: UUID) =
        accountMapper.toDomain(
        accountEntityRepository.findByUserIdAndClientId(id, clientId)
            ?: throw AccountNotFoundException())

    fun list(clientId: UUID) =
        accountEntityRepository
            .findAllByClientIdAndStatusNot(clientId, BasicStatus.DELETED).map {
                accountMapper.toDomain(it)
            }

    fun storeCustomRate(userId: Int, clientId: UUID, addCustomRateRequest: AddCustomRateRequest) =
        accountEntityRepository.findByUserIdAndClientId(userId, clientId).let { account ->
            account ?: throw AccountNotFoundException()

            val ids = addCustomRateRequest.customRates.map { it.crId }
            account.customRate?.removeIf { cr -> cr.id !in ids }

            addCustomRateRequest.customRates.forEach {addCustomRate ->
                if (addCustomRate.crId == null) {
                    account.addCustomRate(addCustomRate)
                    return@forEach
                }

                if (account.customRate == null) account.customRate = mutableListOf()

                account.customRate = account.customRate?.map { cr ->
                    if (cr.id != addCustomRate.crId) return@map cr
                    cr.rate = addCustomRate.rate ?: 0F

                    return@map cr
                }?.toMutableList()
            }
        }
}