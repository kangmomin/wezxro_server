package com.hwalaon.wezxro_server.global.security.jwt.dto

data class TokenDto (
    val accessToken: String?,
    val refreshToken: String?
)