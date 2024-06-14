package com.hwalaon.wezxro_server.domain.order.persistence.adapter

import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.persistence.OrderPersistence
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.WapiOrderPort
import org.springframework.stereotype.Component

@Component
class OrderAdapter(
    private val orderPersistence: OrderPersistence
): WapiOrderPort {
    override fun addOrder(key: String, order: Order, apiServiceId: Long, providerInfo: ProviderApiDto): Long {
        return orderPersistence.add(order, providerInfo, apiServiceId)
    }
}