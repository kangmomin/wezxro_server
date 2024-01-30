package com.hwalaon.wezxro_server.global.common.client.persistence.repository

import com.hwalaon.wezxro_server.global.common.client.persistence.entity.ClientEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClientRepository: JpaRepository<ClientEntity, UUID> {
}