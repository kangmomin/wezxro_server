package com.hwalaon.wezxro_server.global.security.jwt

import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtParser(
    private val jwtProperties: JwtProperties,
    private val principalDetailsService: PrincipalDetailsService
) {

    fun getRefreshTokenBody(refreshToken: String) {
        getTokenBody(refreshToken, jwtProperties.refreshKey).subject
    }

    fun parseRefreshToken(refreshToken: String): String? =
            refreshToken.let {
                if (it.startsWith(JwtProperties.jwtPrefix))
                    it.replace(JwtProperties.jwtPrefix, "")
                else null
            }

    /**
     * request에서 header 분리후 perfix까지 분리
     * prefix 없으면 null 리턴
     */
    fun parseAccessToken(request: HttpServletRequest): String? =
        request.getHeader("X-Auth-Token")
            .let { it ?: return null }
            .let {
                if (it.startsWith(JwtProperties.jwtPrefix))
                    it.replace(JwtProperties.jwtPrefix, "")
                else null
            }

    /**
     * 유저 이메일 반환
      */
    fun authentication(accessOrRefreshToken: String, isAccessToken: Boolean) =
        isAccessToken.let {
            // 엑세스 토큰이 true 일 경우 accessKey 를 할당.
            val secret = if (isAccessToken) {
                jwtProperties.accessKey
            } else {
                jwtProperties.refreshKey
            }

            principalDetailsService.loadUserByUsername(getTokenBody(accessOrRefreshToken, secret).subject)
        }

    private fun getTokenBody(token: String, secret: Key): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body
}