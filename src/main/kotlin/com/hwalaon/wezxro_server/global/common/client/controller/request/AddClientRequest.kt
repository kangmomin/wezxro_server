package com.hwalaon.wezxro_server.global.common.client.controller.request

import jakarta.validation.constraints.NotEmpty

data class AddClientRequest (

    @field:NotEmpty(message = "도메인은 필수 입력 값 입니다.")
    val domain: String,

    @field: NotEmpty(message = "이메일은 필수 입력 값 입니다.")
    val email: String,

    @field: NotEmpty(message = "이메일 비밀번호는 필수 입력 값 입니다.")
    val password: String
)
