package com.hwalaon.wezxro_server.dashboard.persistence.port

import com.hwalaon.wezxro_server.dashboard.controller.response.DashboardResponse
import org.springframework.stereotype.Component

interface OrderPort {
    fun dashboardByUserId(userId: Long): MutableList<DashboardResponse.OrderStatusCntDto>?
}