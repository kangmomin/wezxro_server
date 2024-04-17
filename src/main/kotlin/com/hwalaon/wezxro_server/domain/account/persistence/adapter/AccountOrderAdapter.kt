package com.hwalaon.wezxro_server.domain.account.persistence.adapter

import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.persistence.mapper.AccountMapper
import com.hwalaon.wezxro_server.domain.account.persistence.repository.AccountEntityRepository
import com.hwalaon.wezxro_server.domain.order.persistence.port.AccountPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AccountOrderAdapter(
    private val accountRepository: AccountEntityRepository,
    private val accountMapper: AccountMapper,
): AccountPort {
    override fun boughtMoney(totalCharge: Double, userId: Long): Account? {
        val account = accountRepository.findByIdOrNull(userId) ?: return null

        account.money = account.money!! - totalCharge
        return accountMapper.toDomain(account)
    }
}