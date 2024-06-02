package com.hwalaon.wezxro_server.domain.deposit.controller.response

import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import java.time.LocalDateTime

class DepositListResponse (
    var depositId: String,
    var amount: Long,
    var name: String,
    var status: DepositType,
    var note: String,
    var updatedAt: LocalDateTime
)