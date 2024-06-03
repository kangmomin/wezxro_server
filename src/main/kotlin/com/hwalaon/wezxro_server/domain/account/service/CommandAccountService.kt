package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.*
import com.hwalaon.wezxro_server.domain.account.exception.AccountAlreadyJoinedException
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.model.constant.AccountRole
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
            account.role = AccountRole.USER
            // 비밀번호 암호화 설정
            account.password = passwordEncoder.encode(account.password)

            accountPersistenceAdapter.join(account)
        }


    fun updateAccountInfo(account: Account) {
        if (validAccountUpdate(account)) throw AccountAlreadyJoinedException()
        accountPersistenceAdapter.updateInfo(account).onFailure {
            when (it.message) {
                "not found" -> throw AccountNotFoundException()
            }
        }
    }

    /**
     * 계정이 있을 때 true를 리턴
     */
    private fun validAccount(account: Account): Boolean =
        accountPersistenceAdapter.isExistAccount(account.email!!, account.clientId!!)

    private fun validAccountUpdate(account: Account): Boolean =
        accountPersistenceAdapter.isExistAccount(
            email = account.email!!,
            clientId = account.clientId!!,
            userId = account.userId!!)

    fun deleteAccount(id: Long, clientId: UUID) {
        val account = accountPersistenceAdapter.findById(id, clientId) ?: throw AccountNotFoundException()
        account.status = BasicStatus.DELETED
    }

    fun storeCustomRate(clientId: UUID, addCustomRateRequest: AddCustomRateRequest) {
        accountPersistenceAdapter.storeCustomRate(addCustomRateRequest.userId!!, clientId, addCustomRateRequest)
    }

    fun updateStaticRate(staticRate: Double, userId: Long, clientId: UUID) {
        val target = accountPersistenceAdapter.findById(userId, clientId) ?: throw AccountNotFoundException()
        target.staticRate = staticRate
        accountPersistenceAdapter.updateInfo(target)
    }

    fun addFund(updateMoneyRequest: UpdateMoneyRequest, clientId: UUID, workerId: Long) {
        val password = accountPersistenceAdapter.getPassword(workerId, clientId)

        if (!passwordEncoder.matches(updateMoneyRequest.password, password)) throw AccountNotFoundException()

        accountPersistenceAdapter.addMoney(
            updateMoneyRequest.userId!!, updateMoneyRequest.balance!!, clientId) ?: throw AccountNotFoundException()
    }

    fun setMoney(updateMoneyRequest: UpdateMoneyRequest, clientId: UUID, userId: Long) {
        val password = accountPersistenceAdapter.getPassword(userId, clientId)

        if (!passwordEncoder.matches(updateMoneyRequest.password, password)) throw AccountNotFoundException()

        accountPersistenceAdapter.setMoney(
            updateMoneyRequest.userId!!, updateMoneyRequest.balance!!, clientId) ?: throw AccountNotFoundException()
    }

    fun setPassword(updatePasswordRequest: UpdatePasswordRequest, userId: Long, clientId: UUID) {
        val adminPassword = accountPersistenceAdapter.getPassword(userId, clientId)

        if (!passwordEncoder.matches(updatePasswordRequest.adminPassword, adminPassword)) throw AccountNotFoundException()

        val encodedPassword = passwordEncoder.encode(updatePasswordRequest.newPassword)
        accountPersistenceAdapter
            .setPassword(encodedPassword, updatePasswordRequest.userId, clientId) ?: throw AccountNotFoundException()
    }

    fun updateStatus(clientId: UUID, updateStatusRequest: UpdateStatusRequest) {
        accountPersistenceAdapter.updateStatus(updateStatusRequest, clientId) ?: throw AccountNotFoundException()
    }

    fun deleteCustomRate(userId: Long, clientId: UUID) {
        accountPersistenceAdapter.storeCustomRate(userId, clientId, AddCustomRateRequest(
            userId, ArrayList()
        ))
    }
}