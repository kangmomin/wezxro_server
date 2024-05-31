package com.hwalaon.wezxro_server.domain.account.persistence.port

import com.hwalaon.wezxro_server.domain.account.persistence.port.dto.ServiceRateInfoDto
import org.springframework.stereotype.Component

@Component
interface AccountServicePort {
    fun serviceInfo(serviceIds: List<Long>): MutableList<ServiceRateInfoDto>
}
