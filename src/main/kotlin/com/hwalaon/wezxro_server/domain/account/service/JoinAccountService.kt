package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.JoinRequest
import com.hwalaon.wezxro_server.domain.account.exception.AccountAlreadyJoinedException
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.model.constant.AccountStatus
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistenceAdapter
import com.hwalaon.wezxro_server.global.BasicResponse
import com.hwalaon.wezxro_server.global.annotation.Service
import com.hwalaon.wezxro_server.global.exception.NotEnoughDataException
import com.hwalaon.wezxro_server.global.exception.dto.MsgResponse

@Service
class JoinAccountService(
    private val accountPersistenceAdapter: AccountPersistenceAdapter,
) {

    fun execute(joinRequest: JoinRequest) =
        joinRequest.let {
            val account = joinRequest.toDomain()

            if (account.email == null) throw NotEnoughDataException()
            if (validAccount(account)) throw AccountAlreadyJoinedException()

            account.money = 0.0
            account.status = AccountStatus.ACTIVE
            account.random = "\$a$"

            accountPersistenceAdapter.join(account)
            BasicResponse.created(MsgResponse("회원가입에 성공하였습니다."))
        }

    /**
     * 계정이 있을 때 true를 리턴
     */
    fun validAccount(account: Account): Boolean = accountPersistenceAdapter.isExistAccount(account.email!!)
}