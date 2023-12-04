package com.hwalaon.wezxro_server.domain.account.persistence.mapper

import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.persistence.entity.AccountEntity
import com.hwalaon.wezxro_server.global.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class AccountMapper: BasicMapper<Account, AccountEntity> {
    override fun toDomain(entity: AccountEntity): Account =
        entity.let {
            Account(
                userId = it.userId,
                name = it.name,
                password = it.password,
                random = it.random,
                status = it.status,
                email = it.email,
                money = it.money
            )
        }

    override fun toEntity(domain: Account): AccountEntity =
        domain.let {
            AccountEntity(
                userId = it.userId,
                name = it.name,
                password = it.password,
                random = it.random,
                status = it.status,
                email = it.email,
                money = it.money
            )
        }

}