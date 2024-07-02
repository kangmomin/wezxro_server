package com.hwalaon.wezxro_server.global.common.client.persistence

import com.hwalaon.wezxro_server.global.common.client.mapper.ClientMapper
import com.hwalaon.wezxro_server.global.common.client.model.Client
import com.hwalaon.wezxro_server.global.common.client.persistence.repository.ClientRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class ClientPersistence(
    private val clientRepository: ClientRepository,
    private val mapper: ClientMapper
) {

    /**
     * 등록 가능한 domain이면 true return
     */
    fun validClient(domain: String, email: String) =
        clientRepository.existsByDomainAndEmail(domain, email)

    fun save(client: Client) =
        clientRepository.save(mapper.toEntity(client)).id!!

    fun updateClient(clientId: UUID, email: String, encrypted: String, smtpHost: String): Result<Nothing?> {
        val clientEntity = clientRepository.findByIdOrNull(clientId) ?: return Result.failure(Error())

        clientEntity.host = smtpHost
        clientEntity.email = email
        clientEntity.emailPassword = encrypted

        return Result.success(null)
    }
}