package com.hwalaon.wezxro_server.domain.provider.mapper

import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderEntity
import com.hwalaon.wezxro_server.global.common.basic.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class ProviderMapper: BasicMapper<Provider, ProviderEntity> {
    override fun toDomain(entity: ProviderEntity) =
        Provider(
            id = entity.id,
            apiUrl = entity.apiUrl,
            apiKey = entity.apiKey,
            clientId = entity.clientId,
            name = entity.name,
            balance = entity.balance,
            type = entity.type,
            description = entity.description,
            status = entity.status,
            userId = entity.userId,
        )

    override fun toEntity(domain: Provider) =
        ProviderEntity(
            id = domain.id,
            apiUrl = domain.apiUrl,
            apiKey = domain.apiKey,
            clientId = domain.clientId,
            name = domain.name,
            balance = domain.balance,
            type = domain.type,
            description = domain.description,
            status = domain.status,
            userId = domain.userId,
        )
}