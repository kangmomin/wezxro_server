package com.hwalaon.wezxro_server.domain.wapi.controller.response

data class MultiRefillStatusResponse(
    val refill: Int,
    val status: Any // String or Error Object
)