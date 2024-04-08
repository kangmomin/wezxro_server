package com.hwalaon.wezxro_server.domain.account.controller.response

import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus

data class AccountDetailResponse(
    val userId: Long,
    val name: String,
    val email: String,
    val money: Double,
    val status: BasicStatus,
    val staticRate: Double,
) {
    companion object {
        fun fromDomain(domain: Account) =
            AccountDetailResponse(
                domain.userId!!,
                domain.name!!,
                domain.email!!,
                domain.money!!,
                domain.status!!,
                domain.staticRate!!
            )
    }
}