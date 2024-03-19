package com.hwalaon.wezxro_server.domain.account.persistence.entity

import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "custom_rate")
class CustomRateEntity(
    @Id
    @Column(name = "custom_rate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(nullable = false, updatable = false)
    var serviceId: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    var user: AccountEntity?,
    var rate: Double
): BasicTimeEntity()