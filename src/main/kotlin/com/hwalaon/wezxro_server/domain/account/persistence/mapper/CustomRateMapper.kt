package com.hwalaon.wezxro_server.domain.account.persistence.mapper

import com.hwalaon.wezxro_server.domain.account.model.CustomRate
import com.hwalaon.wezxro_server.domain.account.persistence.entity.CustomRateEntity
import com.hwalaon.wezxro_server.global.common.basic.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class CustomRateMapper(
    private val accountMapper: AccountMapper
): BasicMapper<CustomRate, CustomRateEntity> {
    override fun toDomain(entity: CustomRateEntity) =
        CustomRate(
            id = entity.id,
            rate = entity.rate,
            user = null,
            serviceId = entity.serviceId
        )

    override fun toEntity(domain: CustomRate) =
        CustomRateEntity(
            id = domain.id,
            rate = domain.rate,
            user = null,
            serviceId = domain.serviceId
        )

    fun toEntityWithUser(domain: CustomRate) =
        CustomRateEntity(
            id = domain.id,
            rate = domain.rate,
            user = accountMapper.toEntity(domain.user!!),
            serviceId = domain.serviceId
        )

    fun toDomainWithUser(entity: CustomRateEntity) =
        CustomRate(
            id = entity.id,
            rate = entity.rate,
            user = accountMapper.toDomain(entity.user!!),
            serviceId = entity.serviceId
        )
}