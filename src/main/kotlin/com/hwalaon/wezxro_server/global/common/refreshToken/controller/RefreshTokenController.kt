package com.hwalaon.wezxro_server.global.common.refreshToken.controller

import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.common.refreshToken.controller.request.RefreshTokenRequest
import com.hwalaon.wezxro_server.global.common.refreshToken.controller.response.OnlyAccessTokenResponse
import com.hwalaon.wezxro_server.global.common.refreshToken.service.CommandRefreshTokenService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RefreshTokenController(
    private val commandRefreshTokenService: CommandRefreshTokenService
) {
    @PostMapping("/refresh-auth")
    fun refreshAuth(@RequestBody @Valid refreshTokenRequest: RefreshTokenRequest) =
        BasicResponse.ok(
            OnlyAccessTokenResponse(
                commandRefreshTokenService.authRefreshToken(
                    refreshTokenRequest.refreshToken
                )
            )
        )
}