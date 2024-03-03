package com.hwalaon.wezxro_server.dashboard.port

import com.hwalaon.wezxro_server.dashboard.port.dto.OrderDetailsDto

interface OrderPort {
    fun userOrderDetails(): OrderDetailsDto
}