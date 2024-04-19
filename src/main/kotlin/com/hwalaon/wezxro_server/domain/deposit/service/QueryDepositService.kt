package com.hwalaon.wezxro_server.domain.deposit.service

import com.hwalaon.wezxro_server.domain.deposit.persistence.DepositPersistence
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import java.util.*

@ReadOnlyService
class QueryDepositService(
    private val depositPersistence: DepositPersistence
) {
    fun pendingList(clientId: UUID) =
        depositPersistence.pendingList(clientId)

    fun depositListByUserId(userId: Long) = depositPersistence.pendingListByUserId(userId)
}