package com.hwalaon.wezxro_server.domain.account.model

import com.hwalaon.wezxro_server.domain.account.model.constant.AccountRole
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.model.BasicTime
import java.util.*

data class Account(
    var userId: Long?,
    var name: String?,
    var password: String?,
    var email: String?,
    var random: String?,
    var money: Double?,
    var status: BasicStatus?,
    var clientId: UUID?,
    var staticRate: Double?,
    var role: AccountRole?,
    var key: String?,
): BasicTime()