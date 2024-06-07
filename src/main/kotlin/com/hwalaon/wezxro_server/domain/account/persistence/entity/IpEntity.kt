package com.hwalaon.wezxro_server.domain.account.persistence.entity

import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*

@Entity(name = "ip_log")
class IpEntity (
    @Id @Column(name = "ip_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(length = 20)
    val ip: String?,

    @Column(nullable = false,
        updatable = false,)
    val userId: Long?,
): BasicTimeEntity()