package com.hwalaon.wezxro_server.domain.provider.persistence.entity

import jakarta.persistence.Id
import jakarta.persistence.Index
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash("providerService", timeToLive = 60L * 60 * 24)
class ProviderServiceEntity (

    @Id
    val id: String?,
    @Indexed
    val providerLink: String,
    /** serviceId */
    @Indexed
    val service: String,
    val name: String,
    val type: String,
    val rate: Double,
    val min: Long,
    val max: Long,
    val dripfeed: Boolean,
    val refill: Boolean,
    val cancel: Boolean,
    @Indexed
    val category: String
)