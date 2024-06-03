package com.hwalaon.wezxro_server.domain.order.controller.request

import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

class UpdateOrderRequest (
    @field: NotNull(message = "주문 아이디가 없습니다.")
    val orderId: Long?,

    @field: NotNull(message = "진행된 주문 수량이 없습니다.")
    val startCnt: Long?,

    @field: NotNull(message = "남은 주문 수량이 없습니다.")
    val remains: Long?,

    @field: NotNull(message = "주문 상태가 없습니다.")
    var status: OrderStatus?,

    @field: NotEmpty(message = "타겟 링크가 없습니다.")
    val link: String?
)