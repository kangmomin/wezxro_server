package com.hwalaon.wezxro_server.domain.wapi.persistence.port

import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import org.springframework.stereotype.Component

@Component
interface WapiProviderPort {

    fun getProviderInfoById(id: Long): ProviderApiDto?

}
