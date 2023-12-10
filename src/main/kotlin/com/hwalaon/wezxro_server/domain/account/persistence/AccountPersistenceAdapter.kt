package com.hwalaon.wezxro_server.domain.account.persistence

import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.persistence.mapper.AccountMapper
import com.hwalaon.wezxro_server.domain.account.persistence.repository.AccountRepository
import org.springframework.stereotype.Component

@Component
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository,
    private val accountMapper: AccountMapper
) {
    fun login(loginRequest: LoginRequest) =
        accountRepository.findOneByEmail(loginRequest.email).let {
            if (it == null) throw AccountNotFoundException()
            accountMapper.toDomain(it)
        }
}