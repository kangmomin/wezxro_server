package com.hwalaon.wezxro_server.domain.account.persistence.repository.basicImpl

import com.hwalaon.wezxro_server.domain.account.persistence.entity.QAccountEntity.accountEntity
import com.hwalaon.wezxro_server.domain.account.persistence.repository.custom.AccountEntityRepositoryCustom
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class AccountEntityRepositoryImpl(
    private val query: JPAQueryFactory
): AccountEntityRepositoryCustom {
    // TODO: 전체를 queryDSL로 변환하는 작업 고려.
    // 굳이 queryDSL이랑 JPA가 연결돼야하나?

    override fun isExistAccount(email: String): Boolean =
        query.select(accountEntity.count())
            .from(accountEntity)
            .where(accountEntity.email.eq(email))
            .limit(1)
            .fetchFirst() > 0
}