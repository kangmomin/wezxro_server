package com.hwalaon.wezxro_server.domain.account.persistence.adapter

import com.hwalaon.wezxro_server.domain.account.persistence.repository.AccountEntityRepository
import com.hwalaon.wezxro_server.domain.account.persistence.repository.detailQuery.ValidAccountRepository
import com.hwalaon.wezxro_server.domain.deposit.persistence.port.AccountDepositPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AccountDepositAdapter(
    private val accountRepository: AccountEntityRepository
): AccountDepositPort {
    override fun updateMoney(userId: Long, money: Long): String? {
        val account = accountRepository.findByIdOrNull(userId)
        if (account == null) return null

        account.money = account.money!! + money

        return "success"
    }
}