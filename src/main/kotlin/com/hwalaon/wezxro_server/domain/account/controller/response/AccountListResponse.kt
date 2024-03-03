package com.hwalaon.wezxro_server.domain.account.controller.response

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import java.time.LocalDateTime

data class AccountListResponse (
    val accountList: List<AccountDto>,
    val activateCnt: Long,
    val deactivateCnt: Long,
    val totalCnt: Long,
) {
    class AccountDto(
        val userId: Long,
        val name: String,
        val email: String,
        val balance: Double,
        val staticRate: Float,
        val role: String,
        val createdAt: LocalDateTime,
        val status: BasicStatus,
    )
}