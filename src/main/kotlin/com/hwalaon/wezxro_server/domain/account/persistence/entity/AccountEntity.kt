package com.hwalaon.wezxro_server.domain.account.persistence.entity

import com.hwalaon.wezxro_server.domain.account.model.constant.AccountStatus
import jakarta.persistence.*

@Entity
@Table(name="account")
class AccountEntity(
    @Id @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Int?,

    @Column(nullable = false)
    val name: String?,

    @Column(nullable = false)
    val password: String?,

    @Column(nullable = false,
        updatable = false)
    val email: String?,

    @Column(nullable = false)
    val random: String?,

    @Column(nullable = false)
    val money: Double?,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: AccountStatus?
) {
}