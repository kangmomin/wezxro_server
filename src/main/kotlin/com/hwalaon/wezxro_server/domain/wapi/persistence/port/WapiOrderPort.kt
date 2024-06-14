package com.hwalaon.wezxro_server.domain.wapi.persistence.port

import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import com.hwalaon.wezxro_server.domain.wapi.controller.response.OrderStatusResponse
import org.springframework.stereotype.Component

@Component
interface WapiOrderPort {
    fun addOrder(key: String, order: Order, apiServiceId: Long, providerInfo: ProviderApiDto): Long
    fun orderStatusList(orders: List<Long>): List<OrderStatusResponse>
    fun updateOrderStatus(userId: Long): String?
}
