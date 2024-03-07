package com.hwalaon.wezxro_server.domain.order.mapper

import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.persistence.entity.OrderEntity
import com.hwalaon.wezxro_server.global.common.basic.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class OrderMapper: BasicMapper<Order, OrderEntity> {
    private val prefix = "!!$//$!!"

    override fun toDomain(entity: OrderEntity) =
        Order(
            id = entity.id,
            status = entity.status,
            serviceId = entity.serviceId,
            userId = entity.userId,
            type = entity.type,
            apiOrderId = entity.apiOrderId,
            link = entity.link,
            totalCharge = entity.totalCharge,
            count = entity.count,
            remain = entity.remain,
            startCnt = entity.startCnt,
            answerNumber = entity.answerNumber,
            comments = entity.comments!!.split(prefix),
            groups = entity.groups,
            hashtag = entity.hashtag,
            hashtags = entity.hashtags!!.split(prefix),
            mediaUrl = entity.mediaUrl,
            commentsCustomPackage = entity.commentsCustomPackage,
            username = entity.username,
            usernames = entity.usernames!!.split(prefix),
        ).let {
            it.createdAt = entity.createdAt
            it.updatedAt = entity.updatedAt
            it
        }

    override fun toEntity(domain: Order) =
        OrderEntity(
            id = domain.id,
            status = domain.status,
            serviceId = domain.serviceId,
            userId = domain.userId,
            type = domain.type,
            apiOrderId = domain.apiOrderId,
            link = domain.link,
            totalCharge = domain.totalCharge,
            count = domain.count,
            remain = domain.remain,
            startCnt = domain.startCnt,
            answerNumber = domain.answerNumber,
            comments = domain.comments!!.joinToString(prefix = prefix),
            groups = domain.groups,
            hashtag = domain.hashtag,
            hashtags = domain.hashtags!!.joinToString(prefix = prefix),
            mediaUrl = domain.mediaUrl,
            commentsCustomPackage = domain.commentsCustomPackage,
            username = domain.username,
            usernames = domain.usernames!!.joinToString(prefix = prefix),
        ).let {
            it.createdAt = domain.createdAt
            it.updatedAt = domain.updatedAt
            it
        }
}