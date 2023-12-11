package com.hwalaon.wezxro_server.domain.account.controller.request

import com.hwalaon.wezxro_server.domain.account.persistence.entity.AccountEntity
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class JoinRequest (
    @NotEmpty
    val name: String?,
    @Size(min = 5) @NotEmpty
    val password: String?,
    @Email @NotEmpty
    val email: String?,
) {
    fun toEntity() =
        AccountEntity(
            name = this.name,
            password = this.password,
            email = this.email,
            money = null,
            status = null,
            userId = null,
            random = null,
        )
}
