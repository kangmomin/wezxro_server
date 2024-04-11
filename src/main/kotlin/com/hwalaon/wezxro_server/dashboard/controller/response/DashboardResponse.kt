package com.hwalaon.wezxro_server.dashboard.controller.response

import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.querydsl.core.annotations.QueryProjection

class DashboardResponse
@QueryProjection constructor(
    val balance: Double,
    val totalUsed: Double,
    val totalOrder: Long,
    val orderStatusCnt: List<OrderStatusCntDto>,
) {
    class OrderStatusCntDto
    @QueryProjection constructor(
        val status: OrderStatus,
        val count: Long,
        /**
         * 서식: yyyy-MM-dd
         */
        val date: String,
        val charge: Double
    )
}