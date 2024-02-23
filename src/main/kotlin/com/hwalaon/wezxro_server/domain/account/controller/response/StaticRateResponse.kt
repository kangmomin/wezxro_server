package com.hwalaon.wezxro_server.domain.account.controller.response

import com.hwalaon.wezxro_server.domain.account.model.Account

class StaticRateResponse(
    val email: String,
    val userId: Int,
    val staticRate: Long
) {
    companion object {
        fun fromDomain(domain: Account) =
            StaticRateResponse(
                email = domain.email!!,
                userId = domain.userId!!,
                staticRate = domain.staticRate!!,
            )
    }
}
