package com.hwalaon.wezxro_server.domain.wapi.persistence.port.dto

class WapiServiceDto (
    val id: Long,
    val name: String,
    val type: String,
    val categoryId: Long,
    val refill: Boolean,
    val cancel: Boolean,
    val rate: Double,
    val min: Long,
    val max: Long,
)