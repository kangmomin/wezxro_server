package com.hwalaon.wezxro_server.domain.deposit.service

import com.hwalaon.wezxro_server.domain.deposit.exception.DepositConflictException
import com.hwalaon.wezxro_server.domain.deposit.model.Deposit
import com.hwalaon.wezxro_server.domain.deposit.persistence.DepositPersistence
import com.hwalaon.wezxro_server.global.annotation.CommandService

@CommandService
class CommandDepositService(
    private val depositPersistence: DepositPersistence
) {

    fun save(deposit: Deposit) {
        if (depositPersistence.conflictValid(deposit)) throw DepositConflictException()

        depositPersistence.save(deposit)
    }
}