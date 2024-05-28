package com.hwalaon.wezxro_server.domain.account.persistence.adapter

import com.hwalaon.wezxro_server.dashboard.persistence.port.AccountPort
import com.hwalaon.wezxro_server.dashboard.persistence.port.dto.AccountPayInfoDto
import com.hwalaon.wezxro_server.dashboard.persistence.port.dto.QAccountPayInfoDto
import com.hwalaon.wezxro_server.domain.account.persistence.entity.QAccountEntity.accountEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountDashboardAdapter(
    private val query: JPAQueryFactory
): AccountPort {
    override fun payInfo(userId: Long): AccountPayInfoDto? {
        return query.select(QAccountPayInfoDto(
            accountEntity.money
        )).from(accountEntity)
            .where(accountEntity.userId.eq(userId))
            .fetchOne()
    }

    override fun payInfo(clientId: UUID): AccountPayInfoDto? =
        query.select(QAccountPayInfoDto(
            accountEntity.money.sum()
        )).from(accountEntity)
            .where(accountEntity.clientId.eq(clientId))
            .fetchOne()
}