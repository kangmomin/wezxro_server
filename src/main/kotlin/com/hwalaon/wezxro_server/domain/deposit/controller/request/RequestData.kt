package com.hwalaon.wezxro_server.domain.deposit.controller.request

import java.util.*

data class RequestData(
    val parameterNames: Enumeration<String>,
    val parameterData: MutableMap<String, Array<String>>,
    val requestUrl: String,
)
