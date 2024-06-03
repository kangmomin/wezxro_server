package com.hwalaon.wezxro_server.domain.order.persistence.customRepository

import com.hwalaon.wezxro_server.dashboard.controller.response.DashboardResponse
import com.hwalaon.wezxro_server.dashboard.controller.response.QDashboardResponse_OrderStatusCntDto
import com.hwalaon.wezxro_server.domain.account.persistence.entity.QAccountEntity
import com.hwalaon.wezxro_server.domain.account.persistence.entity.QAccountEntity.accountEntity
import com.hwalaon.wezxro_server.domain.order.controller.response.AdminOrderListResponse
import com.hwalaon.wezxro_server.domain.order.controller.response.OrderListResponse
import com.hwalaon.wezxro_server.domain.order.controller.response.QAdminOrderListResponse
import com.hwalaon.wezxro_server.domain.order.controller.response.QOrderListResponse
import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.hwalaon.wezxro_server.domain.order.persistence.entity.OrderEntity
import com.hwalaon.wezxro_server.domain.order.persistence.entity.QOrderEntity.orderEntity
import com.hwalaon.wezxro_server.domain.provider.persistence.entity.QProviderEntity
import com.hwalaon.wezxro_server.domain.provider.persistence.entity.QProviderEntity.providerEntity
import com.hwalaon.wezxro_server.domain.service.persistence.entity.QServiceEntity.serviceEntity
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomOrderRepository(
    private val query: JPAQueryFactory
) {

    fun orderList(userId: Long?, clientId: UUID): MutableList<OrderListResponse> {
        val query = query.select(
            QOrderListResponse(
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
            .on(
                serviceEntity.id.eq(orderEntity.serviceId),
                serviceEntity.clientId.eq(clientId)
            )

        if (userId != null) query.where(orderEntity.userId.eq(userId))

        return query.orderBy(orderEntity.createdAt.desc())
            .fetch()
    }

    fun adminOrderList(clientId: UUID): MutableList<AdminOrderListResponse> {
        return query.select(
            QAdminOrderListResponse(
                orderEntity.id,
                orderEntity.serviceId,
                serviceEntity.name,
                orderEntity.apiOrderId,
                providerEntity.name,
                orderEntity.info.link,
                orderEntity.count,
                orderEntity.totalCharge,
                orderEntity.startCnt,
                orderEntity.remain,
                orderEntity.createdAt,
                orderEntity.status,
                accountEntity.email
            ))
            .from(orderEntity)
            .join(serviceEntity)
            .on(
                serviceEntity.id.eq(orderEntity.serviceId),
                serviceEntity.clientId.eq(clientId)
            )
            .join(providerEntity)
            .on(
                providerEntity.id.eq(serviceEntity.providerId)
            )
            .join(accountEntity)
            .on(accountEntity.userId.eq(orderEntity.userId))
            .where(orderEntity.status.ne(OrderStatus.DELETED))
            .orderBy(orderEntity.createdAt.desc())
            .fetch()
    }

    fun orderInfoByUserId(userId: Long): MutableList<OrderEntity> =
        query.selectFrom(orderEntity)
            .where(
                orderEntity.userId.eq(userId),
                orderEntity.status.ne(OrderStatus.COMPLETED),
                orderEntity.status.ne(OrderStatus.CANCELED),
                orderEntity.status.ne(OrderStatus.DELETED),
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
            .where(orderEntity.userId.eq(userId),
                orderEntity.status.ne(OrderStatus.DELETED))
            .fetch()
    }

    fun dashboardByClientId(clientId: UUID): MutableList<DashboardResponse.OrderStatusCntDto>? {
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
            .join(accountEntity)
                .on(accountEntity.userId.eq(orderEntity.userId))
            .where(accountEntity.clientId.eq(clientId))
            .fetch()
    }
}