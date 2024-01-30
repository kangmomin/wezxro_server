package com.hwalaon.wezxro_server.domain.account.controller.request

import jakarta.validation.constraints.NotEmpty
import java.util.*

data class LoginRequest (
    @NotEmpty
    val key: UUID,

    @NotEmpty
    val email: String,

    @NotEmpty
    val password: String
)
