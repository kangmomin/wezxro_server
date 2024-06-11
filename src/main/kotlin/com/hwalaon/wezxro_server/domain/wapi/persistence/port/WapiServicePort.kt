package com.hwalaon.wezxro_server.domain.wapi.persistence.port

import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.dto.WapiServiceDto
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface WapiServicePort {

    fun getServices(clientId: UUID): MutableList<WapiServiceDto>

}
