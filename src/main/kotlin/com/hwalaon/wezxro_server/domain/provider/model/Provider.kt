package com.hwalaon.wezxro_server.domain.provider.model

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import java.util.*

data class Provider(
    val id: Long?,
    var userId: Long?,
    val name: String?,
    val description: String?,
    val apiKey: String?,
    val apiUrl: String?,
    val status: BasicStatus?,
    val type: Boolean?,
    var balance: Double?,
    var clientId: UUID?
)