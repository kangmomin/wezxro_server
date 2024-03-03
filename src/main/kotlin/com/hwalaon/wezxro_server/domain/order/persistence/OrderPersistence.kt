package com.hwalaon.wezxro_server.domain.order.persistence

import com.hwalaon.wezxro_server.domain.order.mapper.OrderMapper
import com.hwalaon.wezxro_server.domain.order.model.Order
import com.hwalaon.wezxro_server.domain.order.persistence.customRepository.CustomOrderRepository
import com.hwalaon.wezxro_server.domain.order.persistence.port.ProviderPort
import com.hwalaon.wezxro_server.domain.order.persistence.port.ServicePort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProvideApiDto
import com.hwalaon.wezxro_server.domain.order.persistence.repository.OrderRepository
import com.hwalaon.wezxro_server.global.common.util.ApiProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    private val scope = CoroutineScope(Dispatchers.IO)

    fun orderList(userId: Long, clientId: UUID) =
        customOrderRepository.orderList(userId, clientId)

    fun serviceAddOrderInfo(serviceId: Long) =
        servicePort.serviceAddOrderInfo(serviceId)


    suspend fun add(order: Order, providerInfo: ProvideApiDto, apiServiceId: Long) = scope.async {

        val apiOrderId = ApiProvider(
            apiUrl = providerInfo.apiUrl,
            apiKey = providerInfo.apiKey
        ).addOrder(apiServiceId, order.link!!, order.count!!).toLong()

        order.apiOrderId = apiOrderId

        val orderEntity = orderMapper.toEntity(order)
        val orderId = orderRepository.save(orderEntity).id

        orderId!!
    }

    private fun valid(apiOrderId: Long) =
        orderRepository.existsByApiOrderId(apiOrderId)

    fun providerApiInfo(serviceId: Long) =
        providerPort.providerApiInfo(serviceId)
}