package com.hwalaon.wezxro_server.domain.deposit.controller.request

import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.validation.constraints.NotNull

class UpdateDepositRequest (
    @field: NotNull(message = "충전 기록 ID가 필요합니다.")
    val depositId: Long?,
    @field: NotNull(message = "상태 값이 없습니다.")
    val status: DepositType?,
    @field: NotNull(message = "메모가 없습니다.")
    val note: String?
)
