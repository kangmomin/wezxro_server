package com.hwalaon.wezxro_server.domain.account.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

data class LoginRequest (
    @field:NotNull(message = "클라이언트 키가 없습니다. 관리자에게 문의해주세요.")
    val key: UUID?,

    @field:NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @field:Email(message = "이메일 서식이 아닙니다. 이메일을 제출해주세요.")
    val email: String?,

    @field:NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    val password: String?
)
