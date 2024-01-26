package com.hwalaon.wezxro_server.domain.account.persistence.entity

import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.persistence.*

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
    var status: BasicStatus?
): BasicTimeEntity()