package com.hwalaon.wezxro_server.domain.account.persistence.entity

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="account")
class AccountEntity(
    @Id @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Int?,

    @Column(nullable = false)
    var name: String?,

    @Column(nullable = false)
    var password: String?,

    @Column(nullable = false,
        updatable = false)
    var email: String?,

    @Column(nullable = false)
    var random: String?,

    @Column(nullable = false)
    var money: Double?,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: BasicStatus?,

    @Column(nullable = false)
    var clientId: UUID?
): BasicTimeEntity()