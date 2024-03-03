package com.hwalaon.wezxro_server.domain.order.persistence.customRepository

import com.hwalaon.wezxro_server.domain.order.controller.response.OrderList
import com.hwalaon.wezxro_server.domain.order.controller.response.QOrderList
import com.hwalaon.wezxro_server.domain.order.persistence.entity.QOrderEntity
import com.hwalaon.wezxro_server.domain.order.persistence.entity.QOrderEntity.orderEntity
import com.hwalaon.wezxro_server.domain.service.persistence.entity.QServiceEntity
import com.hwalaon.wezxro_server.domain.service.persistence.entity.QServiceEntity.serviceEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CustomOrderRepository(
    private val query: JPAQueryFactory
) {

    fun orderList(userId: Long, clientId: UUID): MutableList<OrderList> =
        query.select(QOrderList(
            orderEntity.id,
            orderEntity.serviceId,
            serviceEntity.name,
            orderEntity.link,
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
}