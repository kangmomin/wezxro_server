package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.JoinRequest
import com.hwalaon.wezxro_server.domain.account.exception.AccountAlreadyJoinedException
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.CommandService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.exception.NotEnoughDataException
import org.springframework.security.crypto.password.PasswordEncoder

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
        accountPersistenceAdapter.updateInfo(account)
    }

    /**
     * 계정이 있을 때 true를 리턴
     */
    fun validAccount(account: Account): Boolean =
        accountPersistenceAdapter.isExistAccount(account.email!!, account.clientId!!) ||
        accountPersistenceAdapter.isExistName(account.name!!, account.clientId!!)

    fun deleteAccount(id: Int) {
        val account = accountPersistenceAdapter.findById(id)
        account.status = BasicStatus.DELETED
    }
}