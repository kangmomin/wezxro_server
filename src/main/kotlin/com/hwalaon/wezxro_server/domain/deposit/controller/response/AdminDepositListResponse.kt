package com.hwalaon.wezxro_server.domain.deposit.controller.response

import java.time.LocalDateTime

class AdminDepositListResponse (
    var depositId: String,
    var amount: Long,
    var name: String,
    var status: String,
    var note: String,
    var email: String,
    var updatedAt: LocalDateTime
)