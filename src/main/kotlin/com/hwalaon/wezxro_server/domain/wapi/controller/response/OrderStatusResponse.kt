package com.hwalaon.wezxro_server.domain.wapi.controller.response

data class OrderStatusResponse(
    val charge: String,
    val startCount: String,
    val status: String,
    val remains: String,
    val currency: String
)