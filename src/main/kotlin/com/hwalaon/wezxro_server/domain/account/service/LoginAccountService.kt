package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.Service
import com.hwalaon.wezxro_server.global.security.jwt.JwtGenerator
import com.hwalaon.wezxro_server.global.security.jwt.dto.TokenDto
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class LoginAccountService(
    private val accountPersistenceAdapter: AccountPersistenceAdapter,
    private val passwordEncoder: PasswordEncoder,
    private val jwtGenerator: JwtGenerator
) {

    fun execute(loginRequest: LoginRequest): TokenDto {
        val account = accountPersistenceAdapter.login(loginRequest)

        if (!passwordEncoder.matches(loginRequest.password, account.password) &&
            account.userId == null)
            throw AccountNotFoundException()

        return jwtGenerator.generate(account.userId!!)
    }
}