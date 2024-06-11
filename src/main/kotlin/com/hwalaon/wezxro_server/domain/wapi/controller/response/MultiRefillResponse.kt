package com.hwalaon.wezxro_server.domain.wapi.controller.response

data class MultiRefillResponse(
    val order: Int,
    val refill: Any // Int or Error Object
)