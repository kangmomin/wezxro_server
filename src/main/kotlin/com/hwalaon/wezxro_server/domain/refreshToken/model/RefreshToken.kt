package com.hwalaon.wezxro_server.domain.refreshToken.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@Entity
@RedisHash(timeToLive = 129600, )
class RefreshToken (

    @Id
    private var id: String?,
    private var refreshToken: String?,
)