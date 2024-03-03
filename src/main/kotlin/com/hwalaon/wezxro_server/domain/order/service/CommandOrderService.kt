package com.hwalaon.wezxro_server.domain.order.service

import com.hwalaon.wezxro_server.domain.order.controller.request.AddOrderRequest
import com.hwalaon.wezxro_server.domain.order.exception.NotEnoughMoneyException
import com.hwalaon.wezxro_server.domain.order.exception.OrderCountNotValidCountException
import com.hwalaon.wezxro_server.domain.order.persistence.OrderPersistence
import com.hwalaon.wezxro_server.global.annotation.CommandService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*

@CommandService
class CommandOrderService(
    private val orderPersistence: OrderPersistence
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend fun addOrder(addOrderRequest: AddOrderRequest, clientId: UUID, money: Double, userId: Long) {
        if (addOrderRequest.totalCharge!! > money) throw NotEnoughMoneyException()
        val serviceInfo = orderPersistence.serviceAddOrderInfo(addOrderRequest.serviceId!!)

        if (serviceInfo.max < addOrderRequest.count!! ||
            serviceInfo.min < addOrderRequest.count) throw OrderCountNotValidCountException()

        val providerInfo = orderPersistence.providerApiInfo(addOrderRequest.serviceId)

        val order = addOrderRequest.toDomain()
        order.type = serviceInfo.type
        order.userId = userId

        orderPersistence.add(order, providerInfo, serviceInfo.apiServiceId).await()
    }
}