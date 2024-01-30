package com.hwalaon.wezxro_server.domain.account.controller.request

import com.hwalaon.wezxro_server.domain.account.model.Account
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.util.*

data class JoinRequest (
    @NotEmpty
    val key: UUID,

    @NotEmpty
    val name: String?,
    @Size(min = 5) @NotEmpty
    val password: String?,
    @Email @NotEmpty
    val email: String?,
) {
    fun toDomain() =
        Account(
            name = this.name,
            password = this.password,
            email = this.email,
            money = null,
            status = null,
            userId = null,
            random = null,
            clientId = key
        )
}
