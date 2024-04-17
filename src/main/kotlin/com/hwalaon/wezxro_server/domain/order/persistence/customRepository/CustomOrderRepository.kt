package com.hwalaon.wezxro_server.domain.order.persistence.customRepository

import com.hwalaon.wezxro_server.dashboard.controller.response.DashboardResponse
import com.hwalaon.wezxro_server.dashboard.controller.response.QDashboardResponse_OrderStatusCntDto
import com.hwalaon.wezxro_server.domain.order.controller.response.OrderList
import com.hwalaon.wezxro_server.domain.order.controller.response.QOrderList
import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.hwalaon.wezxro_server.domain.order.persistence.entity.OrderEntity
import com.hwalaon.wezxro_server.domain.order.persistence.entity.QOrderEntity.orderEntity
import com.hwalaon.wezxro_server.domain.service.persistence.entity.QServiceEntity.serviceEntity
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomOrderRepository(
    private val query: JPAQueryFactory
) {

    fun orderList(userId: Long, clientId: UUID): MutableList<OrderList> =
        query.select(QOrderList(
            orderEntity.id,
            orderEntity.serviceId,
            serviceEntity.name,
            orderEntity.info.link,
            orderEntity.count,
            orderEntity.totalCharge,
            orderEntity.startCnt,
            orderEntity.remain,
            orderEntity.createdAt,
            orderEntity.status,
        ))
            .from(orderEntity)
            .leftJoin(serviceEntity)
            .on(serviceEntity.id.eq(orderEntity.serviceId),
                serviceEntity.clientId.eq(clientId))
            .where(orderEntity.userId.eq(userId))
            .orderBy(orderEntity.createdAt.desc())
            .fetch()

    fun orderInfoByUserId(userId: Long): MutableList<OrderEntity> =
        query.selectFrom(orderEntity)
            .where(
                orderEntity.userId.eq(userId),
                orderEntity.status.ne(OrderStatus.COMPLETED),
                orderEntity.status.ne(OrderStatus.CANCELED)
            )
            .fetch()

    fun dashboardByUserId(userId: Long): MutableList<DashboardResponse.OrderStatusCntDto>? {
        val dateTemplate =
            Expressions.stringTemplate("TO_CHAR({0}, 'yyyy-mm-dd')", orderEntity.createdAt)

        return query.select(
            QDashboardResponse_OrderStatusCntDto(
                orderEntity.status,
                orderEntity.count,
                dateTemplate,
                orderEntity.totalCharge
            )
        )
            .from(orderEntity)
            .where(orderEntity.userId.eq(userId))
            .fetch()
    }
}