package com.hwalaon.wezxro_server.domain.account.controller.response

import com.hwalaon.wezxro_server.domain.account.model.constant.AccountRole
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime
import java.util.*

data class AccountExportResponse
@QueryProjection constructor(
    var userId: Long?,
    var name: String?,
    var email: String?,
    var money: Double?,
    var status: BasicStatus?,
    var staticRate: Double?,
    var role: AccountRole?,
    var key: String?,
    val lastUpdatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
)