package com.hwalaon.wezxro_server.domain.provider.controller.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import java.util.*

class ProviderListResponse (
    @JsonProperty("providerId")
    val id: Long?,
    val name: String?,
    val description: String?,
    val apiUrl: String,
    val status: BasicStatus,
    var balance: Double,
)