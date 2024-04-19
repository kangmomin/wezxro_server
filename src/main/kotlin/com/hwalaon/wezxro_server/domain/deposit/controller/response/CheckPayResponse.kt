package com.hwalaon.wezxro_server.domain.deposit.controller.response

import kotlinx.serialization.Serializable

@Serializable
data class CheckPayResponse(
    var RCODE: String,
    var PCHK: String,
)