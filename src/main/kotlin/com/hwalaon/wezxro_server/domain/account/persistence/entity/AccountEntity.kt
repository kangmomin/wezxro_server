package com.hwalaon.wezxro_server.domain.account.persistence.entity

import com.hwalaon.wezxro_server.domain.account.controller.request.AddCustomRateRequest
import com.hwalaon.wezxro_server.domain.account.model.constant.AccountRole
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.util.*

@Entity
@Table(name="account")
class AccountEntity(
    @Id @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long?,

    @Column(nullable = false, length = 20)
    var name: String?,

    @Column(nullable = false, columnDefinition = "TEXT")
    var password: String?,

    @Column(nullable = false,
        updatable = false, length = 200)
    var email: String?,

    @Column(nullable = false)
    var random: String?,

    @Column(nullable = false)
    var money: Double?,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: BasicStatus?,

    @Column(nullable = false)
    var clientId: UUID?,

    @Column(nullable = true,
        length = 20)
    var key: String?,

    @Column(nullable = false)
    var staticRate: Double?,

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        mappedBy = "user",
        orphanRemoval = true)
    var customRate: MutableList<CustomRateEntity>?,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("USER")
    var role: AccountRole?,
): BasicTimeEntity() {
    fun addCustomRate(addCustomRateList: AddCustomRateRequest.Companion.AddCustomRateList) {
        val customRateEntity = CustomRateEntity(
            id = null,
            serviceId = addCustomRateList.serviceId,
            rate = addCustomRateList.rate ?: 0.0,
            user = this
        )

        if (this.customRate == null) this.customRate = mutableListOf()

        this.customRate?.add(customRateEntity)
    }
}