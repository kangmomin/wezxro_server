package com.hwalaon.wezxro_server.dashboard.persistence.port

import com.hwalaon.wezxro_server.dashboard.persistence.port.dto.AccountPayInfoDto
import java.util.UUID

interface AccountPort {
    fun payInfo(userId: Long): AccountPayInfoDto?
    fun payInfo(clientId: UUID): AccountPayInfoDto?
}