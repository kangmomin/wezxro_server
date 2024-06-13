package com.hwalaon.wezxro_server.domain.wapi.persistence.port

import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface WapiAccountPort {
    fun getClientIdByUserKey(key: String): UUID?
    fun getUserBalanceByKey(key: String): Double?
}