package com.hwalaon.wezxro_server.domain.refreshToken.model

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(timeToLive = 7200, value = "RefreshToken")
data class RefreshToken (
    @Id
    var id: String?,
)