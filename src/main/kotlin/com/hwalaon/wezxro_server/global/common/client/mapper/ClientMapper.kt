package com.hwalaon.wezxro_server.global.common.client.mapper

import com.hwalaon.wezxro_server.global.common.basic.mapper.BasicMapper
import com.hwalaon.wezxro_server.global.common.client.model.Client
import com.hwalaon.wezxro_server.global.common.client.persistence.entity.ClientEntity
import org.springframework.stereotype.Component

@Component
class ClientMapper: BasicMapper<Client, ClientEntity> {
    override fun toDomain(entity: ClientEntity) =
        Client(entity.id, entity.domain)

    override fun toEntity(domain: Client) =
        ClientEntity(domain.id, domain.domain)
}