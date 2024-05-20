package com.hwalaon.wezxro_server.global.common.util.request

import kotlinx.serialization.Serializable

@Serializable
class CancelOrderRequestDto (
    var key: String?,
    var action: String?,
    var order: String?
)