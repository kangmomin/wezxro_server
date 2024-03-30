package com.hwalaon.wezxro_server.domain.provider.controller.response

class ProviderServiceListResponse (
    val providerLink: String,
    val service: String,
    var name: String,
    var type: String,
    var rate: Double,
    var min: Long,
    var max: Long,
    var dripfeed: Boolean,
    var refill: Boolean,
    var cancel: Boolean,
    var category: String
)