package com.hwalaon.wezxro_server.domain.provider.persistence.entity

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.persistence.*


@Entity
@Table(name = "providers")
data class ProviderEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    val id: Long?,

    @Column(name = "user_id", nullable = false)
    val userId: Long?,

    @Column(length = 50, unique = true, nullable = false)
    val name: String?,

    @Column(length = 200, nullable = false)
    val description: String?,

    @Column(name = "api_key", nullable = false)
    val apiKey: String?,

    @Column(name = "api_url", length = 100, nullable = false)
    val apiUrl: String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: BasicStatus?,

    @Column(nullable = false)
    val type: Boolean?,

    @Column(nullable = false)
    val balance: Double?,
)