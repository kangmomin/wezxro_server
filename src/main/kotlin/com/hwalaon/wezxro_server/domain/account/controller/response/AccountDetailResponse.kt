package com.hwalaon.wezxro_server.domain.account.controller.response

import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus

data class AccountDetailResponse (
    var userId: Int?,
    var name: String?,
    var email: String?,
    var money: Double?,
    var status: BasicStatus?
) {
    companion object {
        fun fromDomain(domain: Account) =
            AccountDetailResponse(
                domain.userId,
                domain.name,
                domain.email,
                domain.money,
                domain.status
            )
    }
}