package com.hwalaon.wezxro_server.domain.provider.model

class ProviderService (
    val id: String?,
    val providerLink: String?,
    val service: String?,
    var name: String?,
    var type: String?,
    var rate: Double?,
    var min: Long?,
    var max: Long?,
    var dripfeed: Boolean?,
    var refill: Boolean?,
    var cancel: Boolean?,
    var category: String?
)
