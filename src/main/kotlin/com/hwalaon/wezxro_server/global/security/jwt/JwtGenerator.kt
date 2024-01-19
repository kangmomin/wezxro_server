package com.hwalaon.wezxro_server.global.security.jwt

import com.hwalaon.wezxro_server.domain.refreshToken.persistence.RefreshTokenPersistenceAdapter
import com.hwalaon.wezxro_server.global.security.jwt.dto.TokenDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtGenerator(
    private val jwtProperties: JwtProperties,
    private val refreshTokenPersistenceAdapter: RefreshTokenPersistenceAdapter
) {
    fun generate(email: String): TokenDto {
        val accessToken = generateToken(email, jwtProperties.accessKey, jwtProperties.accessExp)
        val refreshToken = generateToken(email, jwtProperties.refreshKey, jwtProperties.refreshExp)

        // refresh Token 저장 로직
        refreshTokenPersistenceAdapter.save(refreshToken)

        return TokenDto(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun generateOnlyAccessToken(email: String): String =
        "${jwtProperties.jwtPrefix} ${generateToken(email, jwtProperties.accessKey, jwtProperties.accessExp)}"

    private fun generateToken(email: String, secret: Key, expiredAt: Int) =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS512)
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiredAt * 1000))
            .compact()
}