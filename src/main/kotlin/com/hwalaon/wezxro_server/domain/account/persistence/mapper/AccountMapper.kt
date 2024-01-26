package com.hwalaon.wezxro_server.domain.account.persistence.mapper

import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.persistence.entity.AccountEntity
import com.hwalaon.wezxro_server.global.common.basic.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class AccountMapper: BasicMapper<Account, AccountEntity> {
    override fun toDomain(entity: AccountEntity): Account =
        Account(
            userId = entity.userId,
            name = entity.name,
            password = entity.password,
            random = entity.random,
            status = entity.status,
            email = entity.email,
            money = entity.money
        ).let {
            it.createdAt = entity.createdAt
            it.updatedAt = entity.updatedAt
            it
        }

    override fun toEntity(domain: Account): AccountEntity =
        AccountEntity(
            userId = domain.userId,
            name = domain.name,
            password = domain.password,
            random = domain.random,
            status = domain.status,
            email = domain.email,
            money = domain.money
        ).let {
            it.createdAt = domain.createdAt
            it.updatedAt = domain.updatedAt
            it
        }

}