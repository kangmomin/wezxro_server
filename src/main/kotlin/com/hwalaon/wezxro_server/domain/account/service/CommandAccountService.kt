package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.*
import com.hwalaon.wezxro_server.domain.account.exception.AccountAlreadyJoinedException
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.model.constant.AccountRole
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistence
import com.hwalaon.wezxro_server.global.annotation.CommandService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.exception.NotEnoughDataException
import com.hwalaon.wezxro_server.global.security.jwt.JwtGenerator
import com.hwalaon.wezxro_server.global.security.jwt.dto.TokenDto
import org.springframework.security.crypto.password.PasswordEncoder
import java.security.SecureRandom
import java.util.*

@CommandService
class CommandAccountService(
    private val accountPersistence: AccountPersistence,
    private val passwordEncoder: PasswordEncoder,
    private val jwtGenerator: JwtGenerator
) {
    fun login(loginRequest: LoginRequest, ip: String): TokenDto {
        val account = accountPersistence.login(loginRequest)

        if (passwordEncoder.matches(loginRequest.password, account.password).not() ||
            account.userId == null)
            throw AccountNotFoundException()

        accountPersistence.loginLog(userId = account.userId!!, ip = ip)

        return jwtGenerator.generate(account.userId!!)
    }

    fun join(joinRequest: JoinRequest) {
        val account = joinRequest.toDomain()

        if (account.email == null) throw NotEnoughDataException()
        if (validAccount(account)) throw AccountAlreadyJoinedException()

        account.money = 0.0
        account.status = BasicStatus.ACTIVE
        account.random = "\$a$"
        account.role = AccountRole.USER
        // 비밀번호 암호화 설정
        account.password = passwordEncoder.encode(account.password)

        accountPersistence.join(account)
    }


    fun updateAccountInfo(account: Account) {
        if (validAccountUpdate(account)) throw AccountAlreadyJoinedException()
        accountPersistence.updateInfo(account).onFailure {
            when (it.message) {
                "not found" -> throw AccountNotFoundException()
            }
        }
    }

    /**
     * 계정이 있을 때 true를 리턴
     */
    private fun validAccount(account: Account): Boolean =
        accountPersistence.isExistAccount(account.email!!, account.clientId!!)

    private fun validAccountUpdate(account: Account): Boolean =
        accountPersistence.isExistAccount(
            email = account.email!!,
            clientId = account.clientId!!,
            userId = account.userId!!)

    fun deleteAccount(id: Long, clientId: UUID) {
        val account = accountPersistence.findById(id, clientId) ?: throw AccountNotFoundException()
        account.status = BasicStatus.DELETED
    }

    fun storeCustomRate(clientId: UUID, addCustomRateRequest: AddCustomRateRequest) {
        accountPersistence.storeCustomRate(addCustomRateRequest.userId!!, clientId, addCustomRateRequest)
    }

    fun updateStaticRate(staticRate: Double, userId: Long, clientId: UUID) {
        val target = accountPersistence.findById(userId, clientId) ?: throw AccountNotFoundException()
        target.staticRate = staticRate
        accountPersistence.updateInfo(target)
    }

    fun addFund(updateMoneyRequest: UpdateMoneyRequest, clientId: UUID, workerId: Long) {
        val password = accountPersistence.getPassword(workerId, clientId)

        if (!passwordEncoder.matches(updateMoneyRequest.password, password)) throw AccountNotFoundException()

        accountPersistence.addMoney(
            updateMoneyRequest.userId!!, updateMoneyRequest.balance!!, clientId) ?: throw AccountNotFoundException()
    }

    fun setMoney(updateMoneyRequest: UpdateMoneyRequest, clientId: UUID, userId: Long) {
        val password = accountPersistence.getPassword(userId, clientId)

        if (!passwordEncoder.matches(updateMoneyRequest.password, password)) throw AccountNotFoundException()

        accountPersistence.setMoney(
            updateMoneyRequest.userId!!, updateMoneyRequest.balance!!, clientId) ?: throw AccountNotFoundException()
    }

    fun setPassword(updatePasswordRequest: UpdatePasswordRequest, userId: Long, clientId: UUID) {
        val adminPassword = accountPersistence.getPassword(userId, clientId)

        if (!passwordEncoder.matches(updatePasswordRequest.adminPassword, adminPassword)) throw AccountNotFoundException()

        val encodedPassword = passwordEncoder.encode(updatePasswordRequest.newPassword)
        accountPersistence
            .setPassword(encodedPassword, updatePasswordRequest.userId, clientId) ?: throw AccountNotFoundException()
    }

    fun updateStatus(clientId: UUID, updateStatusRequest: UpdateStatusRequest) {
        accountPersistence.updateStatus(updateStatusRequest, clientId) ?: throw AccountNotFoundException()
    }

    fun deleteCustomRate(userId: Long, clientId: UUID) {
        accountPersistence.storeCustomRate(userId, clientId, AddCustomRateRequest(
            userId, ArrayList()
        )
        )
    }

    fun generateKey(userId: Long): String {
        val key = generateUniqueKey()

        // 재귀 함수를 통해 valid 한 key 값이 나올 때 까지 반복.
        if (accountPersistence.validKey(key) == false) return generateKey(userId)

        accountPersistence.updateKey(userId, key).onFailure {
            when (it.message) {
                "not found" -> throw AccountNotFoundException()
            }
        }

        return key
    }

    private fun generateUniqueKey(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val random = SecureRandom()
        val key = StringBuilder(20)

        val usedChars = mutableSetOf<Char>()

        while (key.length < 20) {
            val randomChar = chars[random.nextInt(chars.length)]
            if (randomChar !in usedChars) {
                key.append(randomChar)
                usedChars.add(randomChar)
            }
        }

        return key.toString()
    }
}