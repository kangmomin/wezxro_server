package com.hwalaon.wezxro_server.domain.account.controller.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

class UpdateMoneyRequest (
    @field: NotNull(message = "타겟 유저의 아이디가 없습니다.")
    val userId: Long?,

    @field: NotNull(message = "추가 금액이 비어있습니다.")
    val balance: Double?,

    @field: NotEmpty(message = "비밀번호가 비어있습니다.")
    val password: String?
)