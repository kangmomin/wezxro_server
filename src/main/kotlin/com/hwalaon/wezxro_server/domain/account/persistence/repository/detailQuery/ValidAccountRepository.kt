package com.hwalaon.wezxro_server.domain.account.persistence.repository.detailQuery

import com.hwalaon.wezxro_server.domain.account.persistence.entity.QAccountEntity.accountEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class ValidAccountRepository(
    private val query: JPAQueryFactory
) {
    fun isExistEmail(email: String, clientId: UUID): Boolean =
        query.select(accountEntity.count())
            .from(accountEntity)
            .where(accountEntity.email.eq(email)
                .and(accountEntity.clientId.eq(clientId))
                .and(accountEntity.status.ne(BasicStatus.DELETED)))
            .limit(1)
            .fetchFirst() > 0

    fun isExistName(name: String, clientId: UUID): Boolean =
        query.select(accountEntity.count())
            .from(accountEntity)
            .where(accountEntity.name.eq(name)
                .and(accountEntity.clientId.eq(clientId))
                .and(accountEntity.status.ne(BasicStatus.DELETED)))
            .limit(1)
            .fetchFirst() > 0

    fun isExistAccountForUpdate(email: String, name: String, clientId: UUID, userId: Long): Boolean =
        query.select(accountEntity.count())
            .from(accountEntity)
            .where(
                accountEntity.userId.ne(userId),
                accountEntity.name.eq(name).or(
                    accountEntity.email.eq(email)))
            .fetchFirst() > 0
}