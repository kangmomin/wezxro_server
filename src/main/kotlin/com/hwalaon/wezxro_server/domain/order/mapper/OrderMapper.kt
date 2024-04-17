package com.hwalaon.wezxro_server.domain.order.mapper

import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.model.OrderInfo
import com.hwalaon.wezxro_server.domain.order.persistence.entity.OrderEntity
import com.hwalaon.wezxro_server.domain.order.persistence.entity.OrderInfoEntity
import com.hwalaon.wezxro_server.global.common.basic.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class OrderMapper(
    private val orderInfoMapper: OrderInfoMapper
): BasicMapper<Order, OrderEntity> {
    private val prefix = "!!$//$!!"

    override fun toDomain(entity: OrderEntity) =
        Order(
            id = entity.id,
            status = entity.status,
            serviceId = entity.serviceId,
            userId = entity.userId,
            type = entity.type,
            apiOrderId = entity.apiOrderId,
            remain = entity.remain,
            startCnt = entity.startCnt,
            count = entity.count,
            totalCharge = entity.totalCharge,
            info = null,
            providerId = entity.providerId
        ).let {
            it.createdAt = entity.createdAt
            it.updatedAt = entity.updatedAt
            it
        }

    override fun toEntity(domain: Order): OrderEntity = this.toEntity(domain, null)
    fun toEntity(domain: Order, info: OrderInfo?): OrderEntity {

        var orderInfoEntity: OrderInfoEntity? = null
        if (info != null) orderInfoEntity = orderInfoMapper.toEntity(info, null)

        val orderEntity = OrderEntity(
            id = domain.id,
            status = domain.status,
            serviceId = domain.serviceId,
            userId = domain.userId,
            type = domain.type,
            apiOrderId = domain.apiOrderId,
            totalCharge = domain.totalCharge,
            count = domain.count,
            remain = domain.remain,
            startCnt = domain.startCnt,
            info = orderInfoEntity,
            providerId = domain.providerId
        ).let {
            it.createdAt = domain.createdAt
            it.updatedAt = domain.updatedAt
            it
        }

        if (info != null) orderInfoEntity = orderInfoMapper.toEntity(info, orderEntity)
        orderEntity.info = orderInfoEntity

        return orderEntity
    }
}