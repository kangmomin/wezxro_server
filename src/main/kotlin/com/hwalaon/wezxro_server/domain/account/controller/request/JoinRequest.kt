package com.hwalaon.wezxro_server.domain.account.controller.request

import com.hwalaon.wezxro_server.domain.account.model.Account
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.*

data class JoinRequest (
    @field:NotNull(message = "key 값은 필수 값 입니다.")
    val key: UUID,

    @field:NotEmpty(message = "이름은 필수 값 입니다.")
    val name: String?,

    @field:Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @field:NotEmpty(message = "비밀번호는 필수 입력 값 입니다.")
    val password: String?,

    @field:Email(message = "이메일 서식이 맞지 않습니다.")
    @field:NotEmpty(message = "이메일은 필수 입력 값 입니다.")
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
            clientId = key,
            staticRate = 0F,
            role = null
        )
}
