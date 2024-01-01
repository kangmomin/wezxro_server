package com.hwalaon.wezxro_server.domain.refreshToken.persistence.repository

import com.hwalaon.wezxro_server.domain.refreshToken.model.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RedisRefreshTokenRepository: CrudRepository<RefreshToken, String> {
}