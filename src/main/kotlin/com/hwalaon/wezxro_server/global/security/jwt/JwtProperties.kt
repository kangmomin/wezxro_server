package com.hwalaon.wezxro_server.global.security.jwt

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtProperties(
    @Value("\${jwt.accessSecret}")
    private val accessSecret: String,
    @Value("\${jwt.refreshSecret}")
    private val refreshSecret: String,
    @Value("\${jwt.accessExp}")
    val accessExp: Int,
    @Value("\${jwt.refreshExp}")
    val refreshExp: Int
) {
    val jwtPrefix: String = "Bearer "

    val accessKey: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret))
    val refreshKey: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret))
}