package com.hwalaon.wezxro_server.domain.account.persistence.repository.basicImpl

import com.hwalaon.wezxro_server.domain.account.persistence.entity.QAccountEntity.accountEntity
import com.hwalaon.wezxro_server.domain.account.persistence.repository.custom.CustomAccountRepository
import com.querydsl.jpa.impl.JPAQueryFactory

class CustomAccountRepositoryBasicImpl(
    private val query: JPAQueryFactory
): CustomAccountRepository {
    override fun isExistAccount(email: String): Boolean =
        query.selectOne()
            .from(accountEntity)
            .fetchOne() == 1
}