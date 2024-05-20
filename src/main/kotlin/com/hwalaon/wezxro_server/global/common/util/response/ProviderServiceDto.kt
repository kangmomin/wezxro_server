package com.hwalaon.wezxro_server.global.common.util.response

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