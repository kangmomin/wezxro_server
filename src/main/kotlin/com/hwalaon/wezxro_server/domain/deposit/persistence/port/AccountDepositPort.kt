package com.hwalaon.wezxro_server.domain.deposit.persistence.port

interface AccountDepositPort {
    fun updateMoney(userId: Long, money: Long): String?
}
