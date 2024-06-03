package com.hwalaon.wezxro_server.domain.service.persistence.port

import org.springframework.stereotype.Component

@Component
interface ServiceAccountPort {
    fun deleteAllCustomRateByServiceId(serviceId: Long)
}
