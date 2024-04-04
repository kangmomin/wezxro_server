package com.hwalaon.wezxro_server.domain.account.controller.response

import com.hwalaon.wezxro_server.domain.account.model.constant.AccountRole
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
        val money: Double,
        val staticRate: Float,
        val role: AccountRole,
        val createdAt: LocalDateTime,
        val status: BasicStatus,
    )
}