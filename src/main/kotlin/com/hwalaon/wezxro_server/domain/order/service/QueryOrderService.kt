package com.hwalaon.wezxro_server.domain.order.service

import com.hwalaon.wezxro_server.domain.order.controller.response.OrderListResponse
import com.hwalaon.wezxro_server.domain.order.persistence.OrderPersistence
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import java.util.*

@ReadOnlyService
class QueryOrderService(
    private val orderPersistence: OrderPersistence,
) {

    fun getMyOrderList(userId: Long, clientId: UUID): MutableList<OrderListResponse> =
        orderPersistence.orderListByUserId(userId, clientId)

    fun getEveryOrderList(clientId: UUID) =
        orderPersistence.orderList(clientId)
}