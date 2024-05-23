package com.hwalaon.wezxro_server.domain.account.controller.request

import jakarta.validation.constraints.NotNull
import java.util.*

data class DemoLoginRequest (
    @field:NotNull(message = "key 값은 필수 값 입니다.")
    val key: UUID?,
)