package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import com.hwalaon.wezxro_server.global.security.exception.ForbiddenException
import com.hwalaon.wezxro_server.global.security.jwt.JwtGenerator
import com.hwalaon.wezxro_server.global.security.jwt.dto.TokenDto
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.crypto.password.PasswordEncoder

@ReadOnlyService
class QueryAccountService(
    private val accountPersistenceAdapter: AccountPersistenceAdapter,
    private val passwordEncoder: PasswordEncoder,
    private val jwtGenerator: JwtGenerator
) {

    fun login(loginRequest: LoginRequest): TokenDto {
        val account = accountPersistenceAdapter.login(loginRequest)

        if (!passwordEncoder.matches(loginRequest.password, account.password) &&
            account.userId == null)
            throw AccountNotFoundException()

        return jwtGenerator.generate(account.userId!!)
    }

    fun detail(id: Int, userInfo: PrincipalDetails) =
        userInfo.let {
            if (userInfo.account.userId == id)
                accountPersistenceAdapter.findById(id)
            else throw ForbiddenException()
        }

    fun adminDetail(id: Int) =
        accountPersistenceAdapter.findById(id)
}