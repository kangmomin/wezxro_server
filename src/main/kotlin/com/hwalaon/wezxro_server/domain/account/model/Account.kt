package com.hwalaon.wezxro_server.domain.account.model

import com.hwalaon.wezxro_server.domain.account.model.constant.AccountStatus

data class Account(
    val userId: Int?,
    val name: String?,
    val password: String?,
    val email: String?,
    val random: String?,
    val money: Double?,
    val status: AccountStatus?
)