package com.hwalaon.wezxro_server.dashboard.port

interface AccountPort {
    fun accountMoney(userId: Long): Double
}