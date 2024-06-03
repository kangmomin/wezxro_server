package com.hwalaon.wezxro_server.domain.account.persistence.adapter

import com.hwalaon.wezxro_server.domain.account.persistence.customRepository.CustomAccountRepository
import com.hwalaon.wezxro_server.domain.account.persistence.mapper.AccountMapper
import com.hwalaon.wezxro_server.domain.account.persistence.repository.AccountEntityRepository
import com.hwalaon.wezxro_server.domain.account.persistence.repository.CustomRateRepository
import com.hwalaon.wezxro_server.domain.service.persistence.port.ServiceAccountPort
import org.springframework.stereotype.Component

@Component
class AccountServiceAdapter(
    private val customRateRepository: CustomRateRepository,
): ServiceAccountPort {
    override fun deleteAllCustomRateByServiceId(serviceId: Long) {
        val crs = customRateRepository.findAllByServiceId(serviceId)

        crs.removeAll(crs)
    }
}