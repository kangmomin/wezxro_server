package com.hwalaon.wezxro_server.domain.account.persistence.customRepository

import com.hwalaon.wezxro_server.domain.account.controller.response.QAccountExportResponse
import com.hwalaon.wezxro_server.domain.account.persistence.entity.QAccountEntity.accountEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomAccountRepository(
    private val query: JPAQueryFactory
) {
    fun getClientIdByUserId(userId: Long): UUID? =
        query.select(accountEntity.clientId)
            .from(accountEntity)
            .where(accountEntity.userId.eq(userId))
            .fetchOne()

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

    fun isExistAccountForUpdate(email: String, clientId: UUID, userId: Long): Boolean =
        query.select(accountEntity.count())
            .from(accountEntity)
            .where(
                accountEntity.userId.ne(userId),
                accountEntity.email.eq(email),
                accountEntity.clientId.eq(clientId))
            .fetchFirst() > 0

    fun getClientIdByUserKey(key: String) =
        query.select(accountEntity.clientId)
            .from(accountEntity)
            .where(
                accountEntity.key.eq(key),
                accountEntity.status.eq(BasicStatus.ACTIVE))
            .fetchOne()

    fun getUserBalance(key: String) =
        query.select(accountEntity.money)
            .from(accountEntity)
            .where(
                accountEntity.key.eq(key),
                accountEntity.status.eq(BasicStatus.ACTIVE)
            ).fetchOne()

    fun getUserIdByKey(key: String) =
        query.select(accountEntity.userId)
            .from(accountEntity)
            .where(
                accountEntity.key.eq(key)
            )
            .fetchOne()

    fun exportList(clientId: UUID) =
        query.select(
            QAccountExportResponse(
                accountEntity.userId,
                accountEntity.name,
                accountEntity.email,
                accountEntity.money,
                accountEntity.status,
                accountEntity.staticRate,
                accountEntity.role,
                accountEntity.key,
                accountEntity.updatedAt,
                accountEntity.createdAt,
            )
        )
            .from(accountEntity)
            .where(accountEntity.clientId.eq(clientId))
            .orderBy(accountEntity.userId.desc())
            .fetch()

}