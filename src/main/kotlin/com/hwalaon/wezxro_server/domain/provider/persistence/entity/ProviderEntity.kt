package com.hwalaon.wezxro_server.domain.provider.persistence.entity

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "providers")
data class ProviderEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    val id: Long?,

    @Column(name = "user_id", nullable = false)
    val userId: Long?,

    @Column(length = 50, nullable = false)
    var name: String?,

    @Column(length = 200)
    var description: String?,

    @Column(name = "api_key", nullable = false)
    var apiKey: String?,

    @Column(name = "api_url", length = 100, nullable = false)
    var apiUrl: String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: BasicStatus?,

    @Column(nullable = false)
    val type: Boolean?,

    @Column(nullable = false)
    var balance: Double?,

    @Column(nullable = false)
    val clientId: UUID?
): BasicTimeEntity()