package com.hwalaon.wezxro_server.domain.account.controller.request

import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UpdateAccountRequest (
    @NotNull
    val userId : Int,

    @Email @Size(min = 6)
    @NotEmpty
    val email: String,

    @Size(min = 8)
    @NotEmpty
    val password: String,

    @NotEmpty
    val name: String,
    val money: Double,
    val status: BasicStatus,
) {
    fun toDomain(): Account =
        Account(
            userId = userId,
            email = email,
            name = name,
            money = money,
            password = password,
            status = status,
            random = null,
            clientId = null
        )
}
