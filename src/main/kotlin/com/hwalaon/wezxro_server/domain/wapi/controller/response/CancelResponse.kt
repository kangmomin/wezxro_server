package com.hwalaon.wezxro_server.domain.wapi.controller.response

data class CancelResponse(
    val order: Int,
    val cancel: Any // Int or Error Object
)