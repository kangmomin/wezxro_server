package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.JoinRequest
import com.hwalaon.wezxro_server.domain.account.exception.AccountAlreadyJoinedException
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.model.constant.AccountStatus
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistenceAdapter
import com.hwalaon.wezxro_server.global.BasicResponse
import com.hwalaon.wezxro_server.global.annotation.Service
import com.hwalaon.wezxro_server.global.constant.BasicStatus
import com.hwalaon.wezxro_server.global.exception.NotEnoughDataException
import com.hwalaon.wezxro_server.global.exception.dto.MsgResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder

@Service
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
            account.status = AccountStatus.ACTIVE
            account.random = "\$a$"
            // 비밀번호 암호화 설정
            account.password = passwordEncoder.encode(account.password)

            accountPersistenceAdapter.join(account)
            BasicResponse.created(MsgResponse("회원가입에 성공하였습니다."))
        }


    fun updateAccountInfo(account: Account): ResponseEntity<Any> {
        accountPersistenceAdapter.updateInfo(account)

        return BasicResponse.ok(MsgResponse("${account.name}님의 계정 정보를 성공적으로 변경하였습니다."))
    }

    /**
     * 계정이 있을 때 true를 리턴
     */
    fun validAccount(account: Account): Boolean =
        accountPersistenceAdapter.isExistAccount(account.email!!) ||
        accountPersistenceAdapter.isExistName(account.name!!)

    fun deleteAccount(id: Int) {
        val account = accountPersistenceAdapter.findById(id)
        account.status = BasicStatus.DELETED
    }
}