package com.hwalaon.wezxro_server.domain.order.persistence

import com.hwalaon.wezxro_server.domain.order.mapper.OrderMapper
import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.model.OrderInfo
import com.hwalaon.wezxro_server.domain.order.persistence.customRepository.CustomOrderRepository
import com.hwalaon.wezxro_server.domain.order.persistence.port.ProviderPort
import com.hwalaon.wezxro_server.domain.order.persistence.port.ServicePort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import com.hwalaon.wezxro_server.domain.order.persistence.repository.OrderRepository
import com.hwalaon.wezxro_server.global.common.util.ApiProvider
import com.hwalaon.wezxro_server.global.common.util.dto.AddOrderInfoDto
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderPersistence(
    private val customOrderRepository: CustomOrderRepository,
    private val orderRepository: OrderRepository,
    private val servicePort: ServicePort,
    private val providerPort: ProviderPort,
    private val orderMapper: OrderMapper,
) {

    fun orderList(userId: Long, clientId: UUID) =
        customOrderRepository.orderList(userId, clientId)

    fun serviceAddOrderInfo(serviceId: Long) =
        servicePort.serviceAddOrderInfo(serviceId)


    fun add(order: Order, providerInfo: ProviderApiDto, apiServiceId: Long): Long {
        val orderInfo = order.info!!

        val apiOrderId = ApiProvider(
            apiUrl = providerInfo.apiUrl,
            apiKey = providerInfo.apiKey
        ).addOrder(
            AddOrderInfoDto(
                apiServiceId.toString(),
                orderInfo.link,
                order.count,
                orderInfo.comments,
                orderInfo.usernames,
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

        val orderEntity = orderMapper.toEntity(order)
        val orderId = orderRepository.save(orderEntity).id

        return orderId!!
    }

    private fun valid(apiOrderId: Long) =
        orderRepository.existsByApiOrderId(apiOrderId)

    fun providerApiInfo(providerId: Long) =
        providerPort.providerApiInfo(providerId)
}