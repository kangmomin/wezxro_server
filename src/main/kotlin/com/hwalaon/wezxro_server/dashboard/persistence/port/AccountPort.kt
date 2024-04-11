package com.hwalaon.wezxro_server.dashboard.persistence.port

import com.hwalaon.wezxro_server.dashboard.persistence.port.dto.AccountPayInfoDto

interface AccountPort {
    fun payInfo(userId: Long): AccountPayInfoDto?
}