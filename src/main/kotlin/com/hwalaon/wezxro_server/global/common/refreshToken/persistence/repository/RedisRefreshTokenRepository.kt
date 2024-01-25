package com.hwalaon.wezxro_server.global.common.refreshToken.persistence.repository

import com.hwalaon.wezxro_server.global.common.refreshToken.model.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RedisRefreshTokenRepository: CrudRepository<RefreshToken, String> {
}