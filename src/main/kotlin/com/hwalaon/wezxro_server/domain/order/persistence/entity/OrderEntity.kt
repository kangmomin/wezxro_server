package com.hwalaon.wezxro_server.domain.order.persistence.entity

import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "\"order\"")
class OrderEntity (
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(
        nullable = false,
        updatable = false,)
    var serviceId: Long?,

    // Provider가 부여한 주문 id
    @Column(
        nullable = false,
        updatable = false,)
    var apiOrderId: Long?,

    @Column(
        nullable = false,
        updatable = false)
    var userId: Long?,

    @Column(
        nullable = false,
        updatable = false,
        unique = true)
    @OneToOne(mappedBy = "order")
    var info: OrderInfoEntity?,

    @Column(nullable = false)
    var totalCharge: Double?,

    @Column(nullable = false)
    var count: Long?,

    // 남은 주문 갯수
    @Column(nullable = false, )
    var remain: Long?,

    // 완료된 주문 갯수
    @Column(nullable = false)
    var startCnt: Long?,

    // 주문 상태
    @Column(nullable = false,)
    @Enumerated(EnumType.STRING)
    var status: OrderStatus?,

    @Column(
        nullable = false,
        updatable = false)
    @Enumerated(EnumType.STRING)
    var type: ServiceType?
): BasicTimeEntity()