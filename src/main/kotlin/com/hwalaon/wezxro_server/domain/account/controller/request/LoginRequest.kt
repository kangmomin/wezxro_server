package com.hwalaon.wezxro_server.domain.account.controller.request

import jakarta.validation.constraints.NotEmpty

data class LoginRequest (
    @NotEmpty
    val email: String,

    @NotEmpty
    val password: String
)
