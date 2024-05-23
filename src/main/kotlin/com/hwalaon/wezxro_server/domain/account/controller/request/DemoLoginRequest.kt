package com.hwalaon.wezxro_server.domain.account.controller.request

import jakarta.validation.constraints.NotNull
import java.util.*

class DemoLoginRequest (
    @field: NotNull(message = "key 값이 없습니다.")
    val key: UUID?
)
