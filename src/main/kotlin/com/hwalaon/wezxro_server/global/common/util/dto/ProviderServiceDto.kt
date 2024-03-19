package com.hwalaon.wezxro_server.global.common.util.dto

import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType

class ProviderServiceDto (
    val service: String,
    val name: String,
    val type: String,
    val rate: Double,
    val min: Long,
    val max: Long,
    val dripfeed: Boolean,
    val refill: Boolean,
    val cancel: Boolean,
    val category: String
)