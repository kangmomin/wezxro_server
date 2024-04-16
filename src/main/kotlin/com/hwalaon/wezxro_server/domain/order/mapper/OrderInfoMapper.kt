package com.hwalaon.wezxro_server.domain.order.mapper

import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.model.OrderInfo
import com.hwalaon.wezxro_server.domain.order.persistence.entity.OrderEntity
import com.hwalaon.wezxro_server.domain.order.persistence.entity.OrderInfoEntity
import com.hwalaon.wezxro_server.global.common.basic.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class OrderInfoMapper : BasicMapper<OrderInfo, OrderInfoEntity> {

    override fun toDomain(entity: OrderInfoEntity) =
        throw IllegalStateException("order 를 입력하여주십시오.")
    override fun toEntity(domain: OrderInfo) =
        throw IllegalStateException("order 를 입력하여주십시오.")

    fun toDomain(entity: OrderInfoEntity, order: Order?) =
        OrderInfo(
            id = entity.id,
            link = entity.link,
            comments = entity.comments,
            commentsCustomPackage = entity.commentsCustomPackage,
            usernames = entity.usernames,
            usernamesCustom = entity.usernamesCustom,
            hashtags = entity.hashtags,
            hashtag = entity.hashtag,
            username = entity.username,
            mediaUrl = entity.mediaUrl,
            subUsername = entity.subUsername,
            subPosts = entity.subPosts,
            subMin = entity.subMin,
            subMax = entity.subMax,
            subDelay = entity.subDelay,
            expiry = entity.expiry,
            order = order
        )

    fun toEntity(domain: OrderInfo, order: OrderEntity?) =
        OrderInfoEntity(
            id = domain.id,
            link = domain.link,
            comments = domain.comments,
            commentsCustomPackage = domain.commentsCustomPackage,
            usernames = domain.usernames,
            usernamesCustom = domain.usernamesCustom,
            hashtags = domain.hashtags,
            hashtag = domain.hashtag,
            username = domain.username,
            mediaUrl = domain.mediaUrl,
            subUsername = domain.subUsername,
            subPosts = domain.subPosts,
            subMin = domain.subMin,
            subMax = domain.subMax,
            subDelay = domain.subDelay,
            expiry = domain.expiry,
            order = order,
        )
}