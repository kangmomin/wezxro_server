package com.hwalaon.wezxro_server.domain.deposit.mapper

import com.hwalaon.wezxro_server.domain.deposit.model.Deposit
import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.DepositEntity
import com.hwalaon.wezxro_server.domain.deposit.persistence.entity.PendingDepositEntity
import com.hwalaon.wezxro_server.global.common.basic.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class DepositMapper: BasicMapper<Deposit, DepositEntity> {
    override fun toDomain(entity: DepositEntity) =
        Deposit(
            entity.id,
            entity.amount,
            entity.name,
            entity.businessName,
            entity.businessPhone,
            entity.businessOwner,
            entity.businessEmail,
            entity.businessRegno,
            entity.personalPhone,
            entity.status,
            entity.type,
            entity.note,
            entity.clientId,
        ).let {
            it.createdAt = entity.createdAt
            it.updatedAt = entity.updatedAt
            it
        }

    override fun toEntity(domain: Deposit) =
        DepositEntity(
            domain.id,
            domain.amount,
            domain.name,
            domain.businessName,
            domain.businessPhone,
            domain.businessOwner,
            domain.businessEmail,
            domain.businessRegno,
            domain.personalPhone,
            domain.status,
            domain.type,
            domain.note,
            domain.clientId
        ).let {
            it.createdAt = domain.createdAt
            it.updatedAt = domain.updatedAt
            it
        }

    fun toRedisEntity(domain: Deposit) =
        PendingDepositEntity(
            id = null,
            domain.amount,
            domain.name,
            domain.businessName,
            domain.businessPhone,
            domain.businessOwner,
            domain.businessEmail,
            domain.businessRegno,
            domain.personalPhone,
            domain.status,
            domain.type,
            domain.note,
            domain.clientId
        )
}