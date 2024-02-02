package com.hwalaon.wezxro_server.domain.service.persistence.entity

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "service")
class ServiceEntity (
    @Id @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    var clientId: UUID?,
    var providerId: Int?,
    var categoryId: Int?,
    var apiServiceId: Int?,
    var name: String?,
    var type: String,
    var rate: Float?,
    var min: Int?,
    var max: Int?,
    var description: String?,
    var status: BasicStatus?,
    var originalRate: Float?
): BasicTimeEntity()