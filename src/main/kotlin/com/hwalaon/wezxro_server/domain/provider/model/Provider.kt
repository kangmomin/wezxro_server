package com.hwalaon.wezxro_server.domain.provider.model

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.persistence.*

data class Provider(
    val id: Long?,
    val userId: Long?,
    val name: String?,
    val description: String?,
    val apiKey: String?,
    val apiUrl: String?,
    val status: BasicStatus?,
    val type: Boolean?,
    val balance: Double?,
)