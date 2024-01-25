package com.hwalaon.wezxro_server.global.common.refreshToken.controller.request

import jakarta.validation.constraints.NotEmpty

data class RefreshTokenRequest (
    @NotEmpty
    val refreshToken: String
)
