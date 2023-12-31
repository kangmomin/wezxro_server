package com.hwalaon.wezxro_server.domain.refreshToken.controller

import com.hwalaon.wezxro_server.domain.refreshToken.service.CommandRefreshTokenService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RefreshTokenController(
    private val commandRefreshTokenService: CommandRefreshTokenService
) {
    @PostMapping("/refresh-auth")
    fun refreshAuth() {
        commandRefreshTokenService.authRefreshToken()
    }
}