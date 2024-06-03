package com.hwalaon.wezxro_server.domain.order.persistence

import com.hwalaon.wezxro_server.domain.order.controller.request.UpdateOrderRequest
import com.hwalaon.wezxro_server.domain.order.exception.OrderNotFoundException
import com.hwalaon.wezxro_server.domain.order.mapper.OrderMapper
import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.hwalaon.wezxro_server.domain.order.persistence.customRepository.CustomOrderRepository
import com.hwalaon.wezxro_server.domain.order.persistence.port.AccountPort
import com.hwalaon.wezxro_server.domain.order.persistence.port.ProviderPort
import com.hwalaon.wezxro_server.domain.order.persistence.port.OrderServicePort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import com.hwalaon.wezxro_server.domain.order.persistence.repository.OrderRepository
import com.hwalaon.wezxro_server.global.common.util.ApiProvider
import com.hwalaon.wezxro_server.global.common.util.request.AddOrderInfoRequestDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*
import javax.security.auth.login.AccountNotFoundException

@Component
class OrderPersistence(
    private val customOrderRepository: CustomOrderRepository,
    private val orderRepository: OrderRepository,
    private val orderServicePort: OrderServicePort,
    private val providerPort: ProviderPort,
    private val accountPort: AccountPort,
    private val orderMapper: OrderMapper,
) {

    fun orderListByUserId(userId: Long, clientId: UUID) =
        customOrderRepository.orderList(userId, clientId)

    fun orderList(clientId: UUID) =
        customOrderRepository.adminOrderList(clientId)

    fun serviceAddOrderInfo(serviceId: Long) =
        orderServicePort.serviceAddOrderInfo(serviceId)


    fun add(order: Order, providerInfo: ProviderApiDto, apiServiceId: Long): Long {
        val orderInfo = order.info!!

        val apiOrderId = ApiProvider(
            apiUrl = providerInfo.apiUrl,
            apiKey = providerInfo.apiKey
        ).addOrder(
            AddOrderInfoRequestDto(
                apiServiceId.toString(),
                orderInfo.link,
                order.count,
                orderInfo.comments,
                orderInfo.usernames,
                orderInfo.hashtag,
                orderInfo.hashtags,
                orderInfo.username,
                orderInfo.mediaUrl,
                orderInfo.subMin,
                orderInfo.subMax,
                orderInfo.subPosts,
                orderInfo.subDelay,
                orderInfo.expiry,
            )
        ).order.toLong()

        order.apiOrderId = apiOrderId

        accountPort.boughtMoney(order.totalCharge!!, order.userId!!)
            ?: throw AccountNotFoundException()

        val orderEntity = orderMapper.toEntity(order, orderInfo)
        val orderId = orderRepository.save(orderEntity).id

        return orderId!!
    }

    private fun valid(apiOrderId: Long) =
        orderRepository.existsByApiOrderId(apiOrderId)

    fun providerApiInfo(providerId: Long) =
        providerPort.providerApiInfo(providerId)

    fun cancelOrder(order: Order): Result<String> {
        val p = providerPort.providerApiInfo(order.providerId!!) ?: return Result.failure(Error("provider not found"))

        val createCancel = ApiProvider(p.apiKey, p.apiUrl).createSingleCancel(order.apiOrderId!!)
        if (createCancel.error != null) {
            print("orderId: ${order.apiOrderId} \n orderExceptionMsg: ${createCancel.cancel}")
            return Result.failure(Error("api exception"))
        }

        val orderEntity = orderRepository.findByIdAndStatusNot(order.id!!) ?: return Result.failure(Error("order not found"))

        orderEntity.status = OrderStatus.CANCELED

        return Result.success("")
    }

    fun orderInfo(orderId: Long): Order? =
        orderRepository.findByIdAndStatusNot(orderId).let {
            if (it == null) return null
            orderMapper.toDomain(it)
        }

    fun isClientMatch(userId: Long, rawClientId: UUID): Boolean? {
        val clientId = accountPort.getClientId(userId) ?: return null

        return clientId == rawClientId
    }

    fun update(updateOrderRequest: UpdateOrderRequest): Result<String> {
        val orderEntity = orderRepository.findByIdAndStatusNot(updateOrderRequest.orderId!!)
            ?: return Result.failure(Error("order not found"))

        orderEntity.remain = updateOrderRequest.remains
        orderEntity.startCnt = updateOrderRequest.startCnt
        orderEntity.status = updateOrderRequest.status ?: orderEntity.status
        orderEntity.info!!.link = updateOrderRequest.link

        return Result.success("")
    }

    fun delete(orderId: Long): Result<Nothing?> {
        val orderEntity = orderRepository.findByIdAndStatusNot(orderId)
            ?: return Result.failure(Error("not found"))

        orderEntity.status = OrderStatus.DELETED

        return Result.success(null)
    }
}