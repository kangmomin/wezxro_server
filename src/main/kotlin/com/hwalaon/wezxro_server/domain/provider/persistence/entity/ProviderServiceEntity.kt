package com.hwalaon.wezxro_server.domain.provider.persistence.entity

import jakarta.persistence.Id
import jakarta.persistence.Index
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash("providerService")
class ProviderServiceEntity (

    @Id
    val id: String?,
    @Indexed
    val providerLink: String,
    /** serviceId */
    @Indexed
    val service: String,
    var name: String,
    var type: String,
    var rate: Double,
    var min: Long,
    var max: Long,
    var dripfeed: Boolean,
    var refill: Boolean,
    var cancel: Boolean,
    @Indexed
    var category: String
)