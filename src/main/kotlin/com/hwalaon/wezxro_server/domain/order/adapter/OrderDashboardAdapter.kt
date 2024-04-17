package com.hwalaon.wezxro_server.domain.order.adapter

import com.hwalaon.wezxro_server.dashboard.controller.response.DashboardResponse
import com.hwalaon.wezxro_server.dashboard.controller.response.QDashboardResponse_OrderStatusCntDto
import com.hwalaon.wezxro_server.dashboard.persistence.port.OrderPort
import com.hwalaon.wezxro_server.dashboard.service.QueryDashboardService
import com.hwalaon.wezxro_server.domain.order.persistence.entity.QOrderEntity
import com.hwalaon.wezxro_server.domain.order.persistence.entity.QOrderEntity.orderEntity
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class OrderDashboardAdapter(
    private val query: JPAQueryFactory
): OrderPort {
    override fun dashboardByUserId(userId: Long): MutableList<DashboardResponse.OrderStatusCntDto>? {
        val dateTemplate =
            Expressions.stringTemplate("TO_CHAR({0}, 'yyyy-mm-dd')", orderEntity.createdAt)

        return query.select(
            QDashboardResponse_OrderStatusCntDto(
                orderEntity.status,
                orderEntity.count,
                dateTemplate,
                orderEntity.totalCharge
            ))
            .from(orderEntity)
            .where(orderEntity.userId.eq(userId))
            .fetch()
    }
}