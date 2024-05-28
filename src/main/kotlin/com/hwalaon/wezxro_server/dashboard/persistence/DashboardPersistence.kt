package com.hwalaon.wezxro_server.dashboard.persistence

import com.hwalaon.wezxro_server.dashboard.persistence.port.AccountPort
import com.hwalaon.wezxro_server.dashboard.persistence.port.OrderPort
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DashboardPersistence (
    private val orderPort: OrderPort,
    private val accountPort: AccountPort,
) {
    fun getOrderDataByUserId(userId: Long) = orderPort.dashboardByUserId(userId)
    fun getOrderDataByClientId(clientId: UUID) = orderPort.dashboardByClientId(clientId)
    fun updateOrderStatus(userId: Long) {
        orderPort.updateOrderStatus(userId)
    }
    fun getPayInfo(userId: Long) = accountPort.payInfo(userId)
    fun getPayInfo(clientId: UUID) = accountPort.payInfo(clientId)

}