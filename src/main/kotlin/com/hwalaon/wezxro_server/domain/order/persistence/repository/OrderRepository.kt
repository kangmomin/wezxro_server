package com.hwalaon.wezxro_server.domain.order.persistence.repository

import com.hwalaon.wezxro_server.domain.order.persistence.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<OrderEntity, Long> {
    fun existsByApiOrderId(apiOrderId: Long): Boolean
}