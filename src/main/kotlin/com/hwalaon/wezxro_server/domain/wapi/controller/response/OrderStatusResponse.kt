package com.hwalaon.wezxro_server.domain.wapi.controller.response

data class OrderStatusResponse(
    val charge: Double,
    val startCount: Long,
    val status: String,
    val remains: String,
    val currency: String
)