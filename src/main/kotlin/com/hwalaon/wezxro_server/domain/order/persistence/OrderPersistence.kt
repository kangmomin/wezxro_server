package com.hwalaon.wezxro_server.domain.order.persistence

import com.hwalaon.wezxro_server.domain.order.mapper.OrderMapper
import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.persistence.customRepository.CustomOrderRepository
import com.hwalaon.wezxro_server.domain.order.persistence.port.AccountPort
import com.hwalaon.wezxro_server.domain.order.persistence.port.ProviderPort
import com.hwalaon.wezxro_server.domain.order.persistence.port.ServicePort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import com.hwalaon.wezxro_server.domain.order.persistence.repository.OrderRepository
import com.hwalaon.wezxro_server.global.common.util.ApiProvider
import com.hwalaon.wezxro_server.global.common.util.dto.AddOrderInfoDto
import org.springframework.stereotype.Component
import java.util.*
import javax.security.auth.login.AccountNotFoundException

@Component
class OrderPersistence(
    private val customOrderRepository: CustomOrderRepository,
    private val orderRepository: OrderRepository,
    private val servicePort: ServicePort,
    private val providerPort: ProviderPort,
    private val accountPort: AccountPort,
    private val orderMapper: OrderMapper,
) {

    fun orderListByUserId(userId: Long, clientId: UUID) =
        customOrderRepository.orderList(userId, clientId)

    fun orderList(clientId: UUID) =
        customOrderRepository.adminOrderList(clientId)

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
}