package com.hwalaon.wezxro_server.domain.account.controller.request

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.validation.constraints.NotNull

class UpdateStatusRequest (
    @field:NotNull(message = "타겟 유저 아이디가 없습니다.")
    val userId: Long?,

    @field: NotNull(message = "Status 가 없습니다.")
    val status: BasicStatus?
)
