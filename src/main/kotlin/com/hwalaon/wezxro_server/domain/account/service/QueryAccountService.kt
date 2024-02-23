package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountListResponse
import com.hwalaon.wezxro_server.domain.account.controller.response.StaticRateResponse
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.security.jwt.JwtGenerator
import com.hwalaon.wezxro_server.global.security.jwt.dto.TokenDto
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@ReadOnlyService
class QueryAccountService(
    private val accountPersistenceAdapter: AccountPersistenceAdapter,
    private val passwordEncoder: PasswordEncoder,
    private val jwtGenerator: JwtGenerator
) {

    fun login(loginRequest: LoginRequest): TokenDto {
        val account = accountPersistenceAdapter.login(loginRequest)

        if (passwordEncoder.matches(loginRequest.password, account.password).not() ||
            account.userId == null)
            throw AccountNotFoundException()

        return jwtGenerator.generate(account.userId!!)
    }

    fun detail(userInfo: PrincipalDetails) =
        accountPersistenceAdapter.findById(userInfo.account.userId ?: 0, userInfo.account.clientId!!)

    fun adminDetail(id: Int, clientId: UUID) =
        accountPersistenceAdapter.findById(id, clientId)

    fun list(clientId: UUID?): AccountListResponse {
        val accountList = accountPersistenceAdapter.list(clientId!!).map {
            AccountListResponse.AccountDto(
                userId = it.userId!!,
                name = it.name!!,
                email = it.email!!,
                status = it.status!!,
                role = "user",
                createdAt = it.createdAt!!,
                staticRate = it.staticRate!!,
                balance = it.money!!,
            )
        }
        var activateCnt: Long = 0
        var deactivateCnt: Long = 0

        accountList.forEach {
            if (it.status == BasicStatus.ACTIVE) ++activateCnt else ++deactivateCnt
        }

        return AccountListResponse(
            accountList = accountList,
            activateCnt = activateCnt,
            deactivateCnt = deactivateCnt,
            totalCnt = activateCnt + deactivateCnt
        )
    }

    fun getStaticRate(clientId: UUID?, userId: Int) =
        accountPersistenceAdapter.findById(userId, clientId!!).let {
            StaticRateResponse.fromDomain(it)
        }
}