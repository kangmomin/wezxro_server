package com.hwalaon.wezxro_server.global.common.util.request

import kotlinx.serialization.Serializable

@Serializable
class OrderRequestDto(
    val action: String,
    val key: String,
    val orders: String
)