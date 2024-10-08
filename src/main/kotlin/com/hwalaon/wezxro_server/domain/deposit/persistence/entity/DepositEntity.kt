package com.hwalaon.wezxro_server.domain.deposit.persistence.entity

import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "deposit")
class DepositEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "depositId")
    var id: Long?,

    @Column(
        nullable = false,
        updatable = false)
    var userId: Long?,

    @Column(nullable = false,)
    /** 입금할 금액 */
    var amount: Long?,
    @Column(nullable = false, length = 100)
    /** 예금주 이름 */
    var name: String?,

    @Column(length = 100)
    var businessEmail: String?,
    var businessPhone: String?,
    var businessOwner: String?,
    @Column(length = 50)
    var businessName: String?,
    @Column(length = 500)
    var businessRegno: String?,
    var personalPhone: String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: DepositType?,
    var type: String?,
    @Column(length = 50)
    var note: String?,
    var clientId: UUID?,
): BasicTimeEntity()