package com.hwalaon.wezxro_server.global.security.jwt

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtProperties {
    @Value("jwt.accessSecret")
    private val accessSecret: String? = "qwertyuiopasdfghjklzxcvbnm12345678900987654321"
    @Value("jwt.refreshSecret")
    private val refreshSecret: String? = "qwertyuiopasdfghjklzxcvbnm12345678900987654321"

    val accessExp = 1800 // 30분
    val refreshExp = 7200 // 30일

    val accessKey: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret))
    val refreshKey: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret))

    companion object {
        const val jwtPrefix: String = "Bearer "
    }
}