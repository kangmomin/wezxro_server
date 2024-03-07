package com.hwalaon.wezxro_server.global.common.refreshToken.persistence.repository

import com.hwalaon.wezxro_server.global.common.refreshToken.model.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RedisRefreshTokenRepository: CrudRepository<RefreshToken, String> {
}