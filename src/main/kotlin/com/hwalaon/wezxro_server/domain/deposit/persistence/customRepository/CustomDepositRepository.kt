package com.hwalaon.wezxro_server.domain.deposit.persistence.customRepository

import com.hwalaon.wezxro_server.domain.deposit.model.Deposit
import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.QDepositEntity
import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.QDepositEntity.depositEntity
import com.querydsl.jpa.impl.JPAQueryFactory

class CustomDepositRepository(
    private val query: JPAQueryFactory
){
}