package com.hwalaon.wezxro_server.global.security.jwt

import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtParser(
    private val jwtProperties: JwtProperties,
    private val principalDetailsService: PrincipalDetailsService
) {
    fun isRefreshTokenExpired(refreshToken: String): Boolean {
        runCatching {
            getTokenBody(refreshToken, jwtProperties.refreshKey).subject
        }.onFailure {
            return true
        }
        return false
    }

    fun parseRefreshToken(refreshToken: String): String? =
            refreshToken.let {
                if (it.startsWith(JwtProperties.jwtPrefix))
                    it.replace(JwtProperties.jwtPrefix, "")
                else null
            }

    fun parseAccessToken(request: HttpServletRequest): String? =
        request.getHeader("X-Auth-Token")
            .let { it ?: return null }
            .let {
                if (it.startsWith(JwtProperties.jwtPrefix))
                    it.replace(JwtProperties.jwtPrefix, "")
                else null
            }

    fun authentication(accessToken: String): Authentication =
        principalDetailsService.loadUserByUsername(getTokenBody(accessToken, jwtProperties.accessKey).subject)
            .let { UsernamePasswordAuthenticationToken(it, "", it.authorities) }

    private fun getTokenBody(token: String, secret: Key): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJwt(token)
            .body
}