package com.hwalaon.wezxro_server.domain.order.service

import com.hwalaon.wezxro_server.domain.order.controller.response.OrderList
import com.hwalaon.wezxro_server.domain.order.mapper.OrderMapper
import com.hwalaon.wezxro_server.domain.order.persistence.OrderPersistence
import com.hwalaon.wezxro_server.domain.order.persistence.customRepository.CustomOrderRepository
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import java.util.*

@ReadOnlyService
class QueryOrderService(
    private val orderPersistence: OrderPersistence,
) {

    fun getMyOrderList(userId: Long, clientId: UUID): MutableList<OrderList> =
        orderPersistence.orderList(userId, clientId)
}