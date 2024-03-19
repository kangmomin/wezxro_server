package com.hwalaon.wezxro_server.global.security.jwt

import com.hwalaon.wezxro_server.global.security.jwt.dto.TokenDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtGenerator(
    private val jwtProperties: JwtProperties,
//    private val refreshTokenPersistenceAdapter: RefreshTokenPersistenceAdapter
) {
    fun generate(userId: Long): TokenDto {
        val accessToken = generateToken(userId, jwtProperties.accessKey, jwtProperties.accessExp)
        val refreshToken = generateToken(userId, jwtProperties.refreshKey, jwtProperties.refreshExp)

        // refresh Token 저장 로직
//        refreshTokenPersistenceAdapter.save(refreshToken)

        return TokenDto(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun generateOnlyAccessToken(userId: Long): String =
        "${jwtProperties.jwtPrefix}${generateToken(userId, jwtProperties.accessKey, jwtProperties.accessExp)}"

    private fun generateToken(userId: Long, secret: Key, expiredAt: Int) =
        "${jwtProperties.jwtPrefix}${
            Jwts.builder()
                .signWith(secret, SignatureAlgorithm.HS512)
                .setSubject(userId.toString())
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + expiredAt * 1000))
                .compact()}"
}