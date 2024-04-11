package com.hwalaon.wezxro_server.dashboard.persistence

import com.hwalaon.wezxro_server.dashboard.controller.response.DashboardResponse
import com.hwalaon.wezxro_server.dashboard.persistence.port.AccountPort
import com.hwalaon.wezxro_server.dashboard.persistence.port.OrderPort
import org.springframework.stereotype.Repository

@Repository
class DashboardPersistence (
    private val orderPort: OrderPort,
    private val accountPort: AccountPort,
) {
    fun getOrderDataByUserId(userId: Long) = orderPort.dashboardByUserId(userId)
    fun getPayInfo(userId: Long) = accountPort.payInfo(userId)

}