package com.hwalaon.wezxro_server.domain.order.controller.response

import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class OrderList @QueryProjection constructor(
    val orderId: Long,
    val serviceId: Long,
    val serviceName: String,
    val link: String,
    val count: Long,
    val charge: Double,
    val startCount: Long,
    val remain: Long,
    val createdAt: LocalDateTime,
    val status: OrderStatus,
)