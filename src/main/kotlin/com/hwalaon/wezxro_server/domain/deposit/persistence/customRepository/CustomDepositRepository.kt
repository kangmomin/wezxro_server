package com.hwalaon.wezxro_server.domain.deposit.persistence.customRepository

import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.DepositEntity
import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.QDepositEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CustomDepositRepository(
    private val query: JPAQueryFactory
){

    fun searchDeposit(clientId: UUID, status: DepositType?): MutableList<DepositEntity> {
        val d = QDepositEntity("deposit_1")

        val sql = query.select(d)
            .from(d)
            .where(d.clientId.eq(clientId))
            .where(d.status.ne(DepositType.DELETE))

        if (status != null) sql.where(d.status.eq(status))

        return sql
            .orderBy(d.id.desc())
            .fetch()
    }
}