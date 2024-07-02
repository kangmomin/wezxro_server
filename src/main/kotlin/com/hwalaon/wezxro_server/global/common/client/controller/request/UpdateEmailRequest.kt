package com.hwalaon.wezxro_server.global.common.client.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.UUID

class UpdateEmailRequest (
    @field: NotNull(message = "클라이언트 아이디가 없습니다.")
    val clientId: UUID?,

    @field: NotEmpty(message = "이메일이 없습니다.")
    @field: Email(message = "이메일 서식에 맞지 않습니다.")
    val email: String?,

    @field: NotEmpty(message = "비밀번호가 없습니다.")
    val password: String?,

    @field: NotEmpty(message = "smtp 서버 주소가 비었습니다.")
    val smtpHost: String?
)
