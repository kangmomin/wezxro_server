package com.hwalaon.wezxro_server.domain.refreshToken.controller.request

import jakarta.validation.constraints.NotEmpty

data class RefreshTokenRequest (
    @NotEmpty
    val refreshToken: String
)
