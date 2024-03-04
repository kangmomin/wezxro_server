package com.hwalaon.wezxro_server.domain.order.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import jakarta.validation.constraints.NotNull

data class AddOrderRequest (
    @JsonProperty("category_id")
    @field:NotNull(message = "카테고리가 누락되었습니다.")
    val categoryId: Long?,
    @JsonProperty("service_id")
    @field:NotNull(message = "서비스가 누락되었습니다.")
    val serviceId: Long?,

    val link: String?,

    @JsonProperty("quantity")
    @field:NotNull(message = "주문 수량이 누락되었습니다.")
    val count: Long?,

    @JsonProperty("total_charge")
    @field:NotNull(message = "총합 금액이 누락되었습니다.")
    val totalCharge: Double?,

    var comments: List<String>?,

    @JsonProperty("comments_custom_package")
    var commentsCustomPackage: String?,
    var hashtags: List<String>?,
    var hashtag: String?,

    @JsonProperty("media_url")
    var mediaUrl: String?,

    var usernames: List<String>?,
    var username: String?,

    @JsonProperty("answer_number")
    var answerNumber: Long?,
    var groups: String?,
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
            link = link,
            totalCharge = totalCharge,
            usernames = usernames,
            username = username,
            commentsCustomPackage = commentsCustomPackage,
            mediaUrl = mediaUrl,
            hashtags = hashtags,
            hashtag = hashtag,
            groups = groups,
            comments = comments,
            answerNumber = answerNumber,
        )
}
