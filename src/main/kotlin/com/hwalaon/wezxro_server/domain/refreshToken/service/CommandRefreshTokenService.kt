package com.hwalaon.wezxro_server.domain.refreshToken.service

import com.hwalaon.wezxro_server.domain.refreshToken.exception.RefreshTokenNotFoundException
import com.hwalaon.wezxro_server.domain.refreshToken.persistence.RefreshTokenPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.Service
import com.hwalaon.wezxro_server.global.security.jwt.JwtGenerator
import com.hwalaon.wezxro_server.global.security.jwt.JwtParser

@Service
class CommandRefreshTokenService(
    private val refreshTokenPersistenceAdapter: RefreshTokenPersistenceAdapter,
    private val jwtGenerator: JwtGenerator,
    private val jwtParser: JwtParser
) {
    fun authRefreshToken(refreshToken: String) {
        if (!refreshTokenPersistenceAdapter.isExistRefreshToken(refreshToken) ||
            !jwtParser.isRefreshTokenExpired(refreshToken)) {
            throw RefreshTokenNotFoundException()
        }


        return jwtGenerator.generate()
    }
}