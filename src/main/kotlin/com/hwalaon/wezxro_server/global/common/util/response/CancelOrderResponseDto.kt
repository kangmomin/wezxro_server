package com.hwalaon.wezxro_server.global.common.util.response
data class CancelOrderResponseDto(
    val order: Int,
    val cancel: Long?,
    val error: String?
)