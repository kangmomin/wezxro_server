package com.hwalaon.wezxro_server.global.common.refreshToken.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(timeToLive = 7200, value = "RefreshToken")
class RefreshToken (
    @Id
    var id: String?,
    var token: String?,
)