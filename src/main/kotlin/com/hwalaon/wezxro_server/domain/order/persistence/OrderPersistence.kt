package com.hwalaon.wezxro_server.domain.order.persistence

import com.hwalaon.wezxro_server.domain.order.persistence.customRepository.CustomOrderRepository
import com.hwalaon.wezxro_server.domain.order.persistence.repository.OrderRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderPersistence(
    private val customOrderRepository: CustomOrderRepository,
    private val orderRepository: OrderRepository
) {

    fun orderList(userId: Long, clientId: UUID) =
        customOrderRepository.orderList(userId, clientId)
}