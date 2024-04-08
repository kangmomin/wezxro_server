package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.AddCustomRateRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.JoinRequest
import com.hwalaon.wezxro_server.domain.account.exception.AccountAlreadyJoinedException
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.CommandService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.exception.NotEnoughDataException
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@CommandService
class CommandAccountService(
    private val accountPersistenceAdapter: AccountPersistenceAdapter,
    private val passwordEncoder: PasswordEncoder
) {

    fun join(joinRequest: JoinRequest) =
        joinRequest.let {
            val account = joinRequest.toDomain()

            if (account.email == null) throw NotEnoughDataException()
            if (validAccount(account)) throw AccountAlreadyJoinedException()

            account.money = 0.0
            account.status = BasicStatus.ACTIVE
            account.random = "\$a$"
            // 비밀번호 암호화 설정
            account.password = passwordEncoder.encode(account.password)

            accountPersistenceAdapter.join(account)
        }


    fun updateAccountInfo(account: Account) {
        if (validAccountUpdate(account)) throw AccountAlreadyJoinedException()
        accountPersistenceAdapter.updateInfo(account)
    }

    /**
     * 계정이 있을 때 true를 리턴
     */
    private fun validAccount(account: Account): Boolean =
        accountPersistenceAdapter.isExistAccount(account.email!!, account.clientId!!) ||
        accountPersistenceAdapter.isExistName(account.name!!, account.clientId!!)

    private fun validAccountUpdate(account: Account): Boolean =
        accountPersistenceAdapter.isExistAccount(
            email = account.email!!,
            clientId = account.clientId!!,
            name = account.name!!,
            userId = account.userId!!)

    fun deleteAccount(id: Long, clientId: UUID) {
        val account = accountPersistenceAdapter.findById(id, clientId) ?: throw AccountNotFoundException()
        account.status = BasicStatus.DELETED
    }

    fun storeCustomRate(clientId: UUID, addCustomRateRequest: AddCustomRateRequest) {
        accountPersistenceAdapter.storeCustomRate(addCustomRateRequest.userId, clientId, addCustomRateRequest)
    }

    fun updateStaticRate(staticRate: Double, userId: Long, clientId: UUID) {
        val target = accountPersistenceAdapter.findById(userId, clientId) ?: throw AccountNotFoundException()
        target.staticRate = staticRate
        accountPersistenceAdapter.updateInfo(target)
    }
}