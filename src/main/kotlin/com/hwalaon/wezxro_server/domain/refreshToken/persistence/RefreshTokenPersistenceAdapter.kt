package com.hwalaon.wezxro_server.domain.refreshToken.persistence

import com.hwalaon.wezxro_server.domain.refreshToken.exception.RefreshTokenNotFoundException
import com.hwalaon.wezxro_server.domain.refreshToken.persistence.repository.RefreshTokenRepository
import org.springframework.stereotype.Component

@Component
class RefreshTokenPersistenceAdapter(
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun isExistRefreshToken(refreshToken: String): Boolean = refreshTokenRepository.existsByRefreshToken(refreshToken)
}