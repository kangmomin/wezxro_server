package com.hwalaon.wezxro_server.domain.order.model

import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.hwalaon.wezxro_server.global.common.basic.model.BasicTime

class Order (
    var id: Long?,
    var serviceId: Long?,
    var providerId: Long?,
    var apiOrderId: Long?,
    var userId: Long?,
    var totalCharge: Double?,
    var count: Long?,
    var remain: Long?,
    var startCnt: Long?,
    var status: OrderStatus?,
    var type: ServiceType?,
    var info: OrderInfo?,
): BasicTime()