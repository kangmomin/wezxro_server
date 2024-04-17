package com.hwalaon.wezxro_server.domain.order.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.model.OrderInfo
import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import jakarta.validation.constraints.NotNull

data class AddOrderRequest (
    @field:NotNull(message = "카테고리가 누락되었습니다.")
    val categoryId: Long?,
    @field:NotNull(message = "서비스가 누락되었습니다.")
    val serviceId: Long?,

    val link: String?,

    @JsonProperty("quantity")
    @field:NotNull(message = "주문 수량이 누락되었습니다.")
    val count: Long?,

    @field: NotNull(message = "총 금액이 누락되었습니다.")
    val totalCharge: Double?,
    var comments: String?,
    var commentsCustomPackage: String?,
    var hashtags: String?,
    var hashtag: String?,
    var subDelay: Long?,
    var subPosts: Long?,
    var subMin: Long?,
    var subMax: Long?,
    var mediaUrl: String?,
    var usernames: String?,
    var username: String?,
    var answerNumber: Long?,
    var groups: String?,
    var expiry: String?,
    var subUsername: String?,
    var usernamesCustom: String?,
) {
    fun toDomain() =
        Order(
            id = null,
            apiOrderId = null,
            type = null,
            userId = null,
            serviceId = this.serviceId,
            remain = count,
            startCnt = 0,
            count = count,
            status = OrderStatus.PENDING,
            info = OrderInfo(
                id = null,
                order = null,
                link = this.link,
                subDelay = this.subDelay,
                expiry = this.expiry,
                subMin = this.subMin,
                subMax = this.subMax,
                subPosts = this.subPosts,
                comments = this.comments,
                username = this.username,
                usernames = this.usernames,
                mediaUrl = this.mediaUrl,
                hashtags = this.hashtags,
                hashtag = this.hashtag,
                subUsername = this.subUsername,
                usernamesCustom = this.usernamesCustom,
                commentsCustomPackage = commentsCustomPackage,
            ),
            totalCharge = this.totalCharge
        )
}
