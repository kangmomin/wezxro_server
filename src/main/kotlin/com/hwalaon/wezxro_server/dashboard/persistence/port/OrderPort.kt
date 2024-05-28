package com.hwalaon.wezxro_server.dashboard.persistence.port

import com.hwalaon.wezxro_server.dashboard.controller.response.DashboardResponse
import java.util.UUID

interface OrderPort {
    fun dashboardByUserId(userId: Long): MutableList<DashboardResponse.OrderStatusCntDto>?
    fun dashboardByClientId(clientId: UUID): MutableList<DashboardResponse.OrderStatusCntDto>?

    fun updateOrderStatus(userId: Long): String?
}