package com.hwalaon.wezxro_server.domain.order.service

import com.hwalaon.wezxro_server.domain.order.controller.request.AddOrderRequest
import com.hwalaon.wezxro_server.domain.order.exception.NotEnoughMoneyException
import com.hwalaon.wezxro_server.domain.order.exception.OrderCountNotValidCountException
import com.hwalaon.wezxro_server.domain.order.persistence.OrderPersistence
import com.hwalaon.wezxro_server.global.annotation.CommandService
import java.util.*

@CommandService
class CommandOrderService(
    private val orderPersistence: OrderPersistence
) {

    fun addOrder(addOrderRequest: AddOrderRequest, clientId: UUID, money: Double, userId: Long): Long {
        if (addOrderRequest.totalCharge!! > money) throw NotEnoughMoneyException()
        val serviceInfo = orderPersistence.serviceAddOrderInfo(addOrderRequest.serviceId!!)

        if (serviceInfo.max < addOrderRequest.count!! ||
            serviceInfo.min < addOrderRequest.count) throw OrderCountNotValidCountException()

        val providerInfo = orderPersistence.providerApiInfo(serviceInfo.providerId)

        val order = addOrderRequest.toDomain()
        order.type = serviceInfo.type
        order.userId = userId

        return orderPersistence.add(order, providerInfo, serviceInfo.apiServiceId)
    }
}