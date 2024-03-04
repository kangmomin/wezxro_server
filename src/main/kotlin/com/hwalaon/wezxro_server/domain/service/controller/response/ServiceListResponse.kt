package com.hwalaon.wezxro_server.domain.service.controller.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ServiceListResponse (
    @JsonProperty("serviceId")
    val id: Long,
    @JsonProperty("name")
    val name: String,
)
