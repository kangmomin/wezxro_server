package com.hwalaon.wezxro_server.domain.order.service

import com.hwalaon.wezxro_server.domain.order.controller.response.OrderList
import com.hwalaon.wezxro_server.domain.order.mapper.OrderMapper
import com.hwalaon.wezxro_server.domain.order.persistence.customRepository.CustomOrderRepository
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import java.util.*

@ReadOnlyService
class QueryOrderService(
    private val orderRepository: CustomOrderRepository,
    private val customOrderRepository: CustomOrderRepository,
    private val orderMapper: OrderMapper
) {

    fun getMyOrderList(userId: Long, clientId: UUID): MutableList<OrderList> =
        customOrderRepository.orderList(userId, clientId)
}