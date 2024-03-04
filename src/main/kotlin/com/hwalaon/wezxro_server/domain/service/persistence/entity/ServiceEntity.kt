package com.hwalaon.wezxro_server.domain.service.persistence.entity

import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "service")
class ServiceEntity (
    @Id @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(nullable = false)
    var clientId: UUID?,

    @Column(nullable = false)
    var providerId: Long?,
    @Column(nullable = false)
    var categoryId: Long?,

    @Column(nullable = false)
    var apiServiceId: Long?,

    @Column(nullable = false)
    var name: String?,
    @Column(nullable = false)
    var type: ServiceType?,

    @Column(nullable = false)
    var rate: Float?,

    @Column(nullable = false)
    var min: Long?,

    @Column(nullable = false)
    var max: Long?,

    @Column(nullable = false)
    var description: String?,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: BasicStatus?,

    @Column(nullable = false)
    var originalRate: Float?
): BasicTimeEntity()