package com.hwalaon.wezxro_server.domain.deposit.model

import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import com.hwalaon.wezxro_server.global.common.basic.model.BasicTime
import java.util.*

class Deposit (
    var id: Long?,
    var userId: Long?,
    var amount: Long?,
    var name: String?,
    var businessEmail: String?,
    var businessPhone: String?,
    var businessOwner: String?,
    var businessName: String?,
    var businessRegno: String?,
    var personalPhone: String?,
    var status: DepositType?,
    var type: String?,
    var note: String?,
    var clientId: UUID?,
): BasicTime()