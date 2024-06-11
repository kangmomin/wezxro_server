package com.hwalaon.wezxro_server.domain.account.persistence.adapter

import com.hwalaon.wezxro_server.domain.account.persistence.customRepository.CustomAccountRepository
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.WapiAccountPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountWapiAdapter(
    private val customAccountRepository: CustomAccountRepository
): WapiAccountPort {
    override fun getClientIdByUserKey(key: String): UUID? =
        customAccountRepository.getClientIdByUserKey(key)
}