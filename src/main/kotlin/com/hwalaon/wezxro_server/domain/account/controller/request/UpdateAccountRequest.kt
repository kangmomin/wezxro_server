package com.hwalaon.wezxro_server.domain.account.controller.request

import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UpdateAccountRequest (
    @field:NotNull
    val userId: Long,

    @field:Email(message = "이메일 서식이 맞지 않습니다.")
    @field:Size(min = 6, message = "이메일은 6자 이상이어야 합니다.")
    @field:NotEmpty(message = "이메일은 필수 값입니다.")
    val email: String,

    @field:Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @field:NotEmpty(message = "비밀번호는 필수 값입니다.")
    val password: String,

    @field:NotEmpty(message = "이름은 필수 값입니다.")
    val name: String,

    @field:NotNull(message = "보유액은 필수 값입니다.")
    val money: Double,

    @field:NotNull(message = "상태 값은 필수 값입니다.")
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
            clientId = null,
            staticRate = null,
        )
}
