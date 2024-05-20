package com.hwalaon.wezxro_server.domain.order.persistence.port

import com.hwalaon.wezxro_server.domain.account.model.Account
import java.util.*

interface AccountPort {
    fun boughtMoney(totalCharge: Double, userId: Long): Account?

    /** user를 찾을 수 없으면 null return */
    fun getClientId(userId: Long): UUID?
}