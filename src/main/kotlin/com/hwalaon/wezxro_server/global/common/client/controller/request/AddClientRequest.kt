package com.hwalaon.wezxro_server.global.common.client.controller.request

import jakarta.validation.constraints.NotEmpty

data class AddClientRequest (

    @field:NotEmpty(message = "도메인은 필수 입력 값 입니다.")
    val domain: String
)
