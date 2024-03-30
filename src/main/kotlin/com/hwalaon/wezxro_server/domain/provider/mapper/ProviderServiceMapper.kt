package com.hwalaon.wezxro_server.domain.provider.mapper

import com.hwalaon.wezxro_server.domain.provider.model.ProviderService
import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderServiceEntity
import com.hwalaon.wezxro_server.global.common.basic.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class ProviderServiceMapper : BasicMapper<ProviderService, ProviderServiceEntity> {
    override fun toDomain(entity: ProviderServiceEntity) =
        ProviderService(
            id = entity.id,
            providerLink = entity.providerLink,
            service = entity.service,
            name = entity.name,
            type = entity.type,
            rate = entity.rate,
            min = entity.min,
            max = entity.max,
            dripfeed = entity.dripfeed,
            refill = entity.refill,
            cancel = entity.cancel,
            category = entity.category
        )

    override fun toEntity(domain: ProviderService) =
        ProviderServiceEntity(
            id = domain.id,
            providerLink = domain.providerLink,
            service = domain.service,
            name = domain.name,
            type = domain.type,
            rate = domain.rate,
            min = domain.min,
            max = domain.max,
            dripfeed = domain.dripfeed,
            refill = domain.refill,
            cancel = domain.cancel,
            category = domain.category
        )
}
