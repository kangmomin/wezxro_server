package com.hwalaon.wezxro_server.global.common.client.persistence.adapter

import com.hwalaon.wezxro_server.domain.account.persistence.port.AccountClientPort
import com.hwalaon.wezxro_server.domain.account.persistence.port.dto.ClientEmailInfo
import com.hwalaon.wezxro_server.global.common.client.persistence.repository.ClientRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class ClientAdapter(
    private val clientRepository: ClientRepository
): AccountClientPort {
    override fun getClientEmailInfo(clientId: UUID): ClientEmailInfo? {
        val client = clientRepository.findByIdOrNull(clientId) ?: return null

        return ClientEmailInfo(
            client.email!!,
            client.emailPassword!!,
            client.email!!.split("@")[1]
        )
    }
}