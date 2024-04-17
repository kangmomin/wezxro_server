package com.hwalaon.wezxro_server.domain.order.persistence.port

import com.hwalaon.wezxro_server.domain.account.model.Account

interface AccountPort {
    fun boughtMoney(totalCharge: Double, userId: Long): Account?
}