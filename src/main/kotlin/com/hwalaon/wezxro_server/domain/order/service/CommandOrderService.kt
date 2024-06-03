package com.hwalaon.wezxro_server.domain.order.service

import com.hwalaon.wezxro_server.domain.order.controller.request.AddOrderRequest
import com.hwalaon.wezxro_server.domain.order.controller.request.UpdateOrderRequest
import com.hwalaon.wezxro_server.domain.order.exception.NotEnoughMoneyException
import com.hwalaon.wezxro_server.domain.order.exception.OrderCountNotValidCountException
import com.hwalaon.wezxro_server.domain.order.exception.OrderNotFoundException
import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.hwalaon.wezxro_server.domain.order.persistence.OrderPersistence
import com.hwalaon.wezxro_server.domain.provider.exception.ProviderNotFoundException
import com.hwalaon.wezxro_server.global.annotation.CommandService
import com.hwalaon.wezxro_server.global.common.exception.ApiRequestFailedException
import com.hwalaon.wezxro_server.global.common.exception.ForbiddenException
import java.util.*
import javax.security.auth.login.AccountNotFoundException

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
            ?: throw ProviderNotFoundException()

        val order = addOrderRequest.toDomain()
        order.type = serviceInfo.type
        order.providerId = serviceInfo.providerId
        order.userId = userId
        order.info!!.order = order

        return orderPersistence.add(order, providerInfo, serviceInfo.apiServiceId)
    }

    fun cancelOrder(orderId: Long, clientId: UUID) {
        val orderInfo = orderPersistence.orderInfo(orderId) ?: throw OrderNotFoundException()
        val clientMatch = orderPersistence.isClientMatch(orderInfo.userId!!, clientId) ?: throw AccountNotFoundException()
        if (!clientMatch) throw ForbiddenException()
        orderPersistence.cancelOrder(orderInfo).onFailure {
            when (it.message) {
                "order not found" -> throw OrderNotFoundException()
                "api exception" -> throw ApiRequestFailedException()
                "provider not found" -> throw ProviderNotFoundException()
            }
        }
    }

    fun updateOrder(updateOrderRequest: UpdateOrderRequest) {
        if (updateOrderRequest.status == OrderStatus.CANCELED) updateOrderRequest.status = null
        orderPersistence.update(updateOrderRequest)
            .onFailure {
                when (it.message) {
                    "order not found" -> throw OrderNotFoundException()
                }
            }

        return
    }
}