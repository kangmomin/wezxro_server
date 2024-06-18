package com.hwalaon.wezxro_server.domain.account.persistence.port

import com.hwalaon.wezxro_server.domain.account.persistence.port.dto.ClientEmailInfo
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface AccountClientPort {
    fun getClientEmailInfo(clientId: UUID): ClientEmailInfo?

}
