package com.hwalaon.wezxro_server.domain.provider.controller.response

import com.fasterxml.jackson.annotation.JsonProperty

class ProviderListResponse (
    @JsonProperty("providerId")
    val id: Long?,
    val name: String?,
    val description: String?,
    val apiUrl: String,
    val status: Int,
    var balance: Double,
)