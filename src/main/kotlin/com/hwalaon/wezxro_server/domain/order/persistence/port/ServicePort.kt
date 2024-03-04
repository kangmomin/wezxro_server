package com.hwalaon.wezxro_server.domain.order.persistence.port

import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ServiceAddOrderInfoDto
import org.springframework.stereotype.Component

@Component
interface ServicePort {
    fun serviceAddOrderInfo(serviceId: Long): ServiceAddOrderInfoDto
}
