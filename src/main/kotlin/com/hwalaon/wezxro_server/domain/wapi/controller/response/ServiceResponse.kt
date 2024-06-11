package com.hwalaon.wezxro_server.domain.wapi.controller.response

data class ServiceResponse(
    val service: Long,
    val name: String,
    val type: String,
    val category: String,
    val rate: Double,
    val min: Long,
    val max: Long,
    val refill: Boolean,
    val cancel: Boolean
)