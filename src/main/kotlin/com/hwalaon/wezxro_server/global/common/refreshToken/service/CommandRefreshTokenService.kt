package com.hwalaon.wezxro_server.global.common.refreshToken.service

import com.hwalaon.wezxro_server.global.common.refreshToken.controller.response.OnlyAccessTokenResponse
import com.hwalaon.wezxro_server.global.common.refreshToken.exception.RefreshTokenNotFoundException
import com.hwalaon.wezxro_server.global.common.refreshToken.persistence.RefreshTokenPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.Service
import com.hwalaon.wezxro_server.global.security.jwt.JwtGenerator
import com.hwalaon.wezxro_server.global.security.jwt.JwtParser

@Service
class CommandRefreshTokenService(
    private val refreshTokenPersistenceAdapter: RefreshTokenPersistenceAdapter,
    private val jwtGenerator: JwtGenerator,
    private val jwtParser: JwtParser
) {
    fun authRefreshToken(refreshToken: String): OnlyAccessTokenResponse {
        val existRefreshToken = refreshTokenPersistenceAdapter.isExistRefreshToken(refreshToken)
        if (!existRefreshToken) {
            throw RefreshTokenNotFoundException()
        }

        // refreshToken에서 userId 가져오기
        val userId = jwtParser.authentication(refreshToken, isAccessToken = false).toInt()

        return OnlyAccessTokenResponse(
            jwtGenerator.generateOnlyAccessToken(userId)
        )
    }
}