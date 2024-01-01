package com.hwalaon.wezxro_server.domain.refreshToken.service

import com.hwalaon.wezxro_server.domain.refreshToken.controller.response.OnlyAccessTokenResponse
import com.hwalaon.wezxro_server.domain.refreshToken.exception.RefreshTokenNotFoundException
import com.hwalaon.wezxro_server.domain.refreshToken.persistence.RefreshTokenPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.Service
import com.hwalaon.wezxro_server.global.security.jwt.JwtGenerator
import com.hwalaon.wezxro_server.global.security.jwt.JwtParser
import com.hwalaon.wezxro_server.global.security.jwt.dto.TokenDto

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

        // refreshToken에서 email 가져오기
        val refreshTokenBody = jwtParser.authentication(refreshToken, isAccessToken = false)

        return OnlyAccessTokenResponse(
            jwtGenerator.generateOnlyAccessToken(refreshTokenBody.username)
        )
    }
}