package com.hwalaon.wezxro_server.domain.deposit.persistence.entity

import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "deposit", timeToLive = 60 * 10)
class PendingDepositEntity(
    @Id
    var id: Long?,
    var amount: Long?,
    var name: String?,
    var businessEmail: String?,
    var businessPhone: String?,
    var businessOwner: String?,
    var businessName: String?,
    var businessRegno: String?,
    var personalPhone: String?,
    var status:DepositType?,
    var type: String?,
    var note: String?,
)