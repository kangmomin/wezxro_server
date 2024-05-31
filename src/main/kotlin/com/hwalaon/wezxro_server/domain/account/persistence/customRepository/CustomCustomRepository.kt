package com.hwalaon.wezxro_server.domain.account.persistence.customRepository

import com.hwalaon.wezxro_server.domain.account.controller.response.CustomRateInfoResponse
import com.hwalaon.wezxro_server.domain.account.controller.response.QCustomRateInfoResponse
import com.hwalaon.wezxro_server.domain.account.persistence.entity.QAccountEntity
import com.hwalaon.wezxro_server.domain.account.persistence.entity.QCustomRateEntity
import com.hwalaon.wezxro_server.domain.account.persistence.entity.QCustomRateEntity.customRateEntity
import com.hwalaon.wezxro_server.domain.service.persistence.entity.QServiceEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.querydsl.core.types.Expression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CustomCustomRepository(
    private val query: JPAQueryFactory
) {

    fun getCustomRates(userId: Long, clientId: UUID): MutableList<CustomRateInfoResponse> {
        val cr = QCustomRateEntity("cr1")

        return query.select(
            QCustomRateInfoResponse(
                cr.id,
                cr.serviceId,
                Expressions.nullExpression(),
                Expressions.nullExpression(),
                cr.rate
            )
        )
            .from(cr)
            .where(
                cr.user.userId.eq(userId),
                cr.user.clientId.eq(clientId),
                cr.user.status.ne(BasicStatus.DELETED)
            )
            .orderBy(
                cr.createdAt.desc()
            )
            .fetch() ?: ArrayList()
    }
}