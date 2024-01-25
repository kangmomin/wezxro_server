package com.hwalaon.wezxro_server.global.common.refreshToken.persistence

import com.hwalaon.wezxro_server.global.common.refreshToken.model.RefreshToken
import com.hwalaon.wezxro_server.global.common.refreshToken.persistence.repository.RedisRefreshTokenRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class RefreshTokenPersistenceAdapter(
    private val refreshTokenRepository: RedisRefreshTokenRepository
) {

    fun isExistRefreshToken(refreshToken: String): Boolean = refreshTokenRepository.findByIdOrNull(refreshToken) != null
    fun save(refreshToken: String) = refreshTokenRepository.save(
        // id 자동 생성을 위한 null 처리
        RefreshToken(id = refreshToken)
    )
}