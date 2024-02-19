package com.hwalaon.wezxro_server.global.common.client.service

import com.hwalaon.wezxro_server.global.annotation.CommandService
import com.hwalaon.wezxro_server.global.common.client.exception.ClientConflictException
import com.hwalaon.wezxro_server.global.common.client.model.Client
import com.hwalaon.wezxro_server.global.common.client.persistence.ClientPersistence
import java.util.*

@CommandService
class ClientService(
    private val clientPersistence: ClientPersistence
) {

    fun addClient(domain: String): UUID? {
        if (!clientPersistence.validClient(domain))
            throw ClientConflictException()

        return clientPersistence.save(Client(domain))
    }
}