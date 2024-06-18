package com.hwalaon.wezxro_server.global.common.client.persistence

import com.hwalaon.wezxro_server.global.common.client.mapper.ClientMapper
import com.hwalaon.wezxro_server.global.common.client.model.Client
import com.hwalaon.wezxro_server.global.common.client.persistence.repository.ClientRepository
import org.springframework.stereotype.Component

@Component
class ClientPersistence(
    private val clientRepository: ClientRepository,
    private val mapper: ClientMapper
) {

    /**
     * 등록 가능한 domain이면 true return
     */
    fun validClient(domain: String, email: String) =
        !clientRepository.existsByDomainAndEmail(domain, email)

    fun save(client: Client) =
        clientRepository.save(mapper.toEntity(client)).id!!
}