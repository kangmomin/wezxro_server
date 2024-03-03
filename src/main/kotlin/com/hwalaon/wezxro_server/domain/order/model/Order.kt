package com.hwalaon.wezxro_server.domain.order.model

import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.hwalaon.wezxro_server.domain.order.model.constant.OrderType
import com.hwalaon.wezxro_server.global.common.basic.model.BasicTime

class Order (
    var id: Long?,
    var serviceId: Long?,
    var apiOrderId: Long?,
    var userId: Long?,
    var totalCharge: Double?,
    var count: Long?,
    var link: String?,
    var remain: Long?,
    var startCnt: Long?,
    var status: OrderStatus?,
    var type: OrderType?
): BasicTime()