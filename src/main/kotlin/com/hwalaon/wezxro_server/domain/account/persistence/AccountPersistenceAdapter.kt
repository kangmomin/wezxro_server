package com.hwalaon.wezxro_server.domain.account.persistence

import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.persistence.mapper.AccountMapper
import com.hwalaon.wezxro_server.domain.account.persistence.repository.AccountEntityRepository
import com.hwalaon.wezxro_server.domain.account.persistence.repository.custom.AccountEntityRepositoryCustom
import org.springframework.stereotype.Component

@Component
class AccountPersistenceAdapter(
    private val accountEntityRepository: AccountEntityRepository,
    private val accountMapper: AccountMapper,
    private val accountEntityRepositoryCustom: AccountEntityRepositoryCustom
) {
    fun login(loginRequest: LoginRequest) =
        accountEntityRepository.findOneByEmail(loginRequest.email).let {
            if (it == null) throw AccountNotFoundException()
            accountMapper.toDomain(it)
        }

    fun join(account: Account) =
        accountMapper.toEntity(account).let {
            accountEntityRepository.save(it)
        }

    fun isExistAccount(email: String) = accountEntityRepositoryCustom.isExistAccount(email)
}