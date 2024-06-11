package com.hwalaon.wezxro_server.domain.wapi.persistence

import com.hwalaon.wezxro_server.domain.wapi.persistence.port.WapiAccountPort
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.WapiCategoryPort
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.WapiServicePort
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.dto.WapiServiceDto
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WapiPersistence(
    private val wapiServicePort: WapiServicePort,
    private val wapiCategoryPort: WapiCategoryPort,
    private val wapiAccountPort: WapiAccountPort
) {
    fun services(clientId: UUID): MutableList<WapiServiceDto> {
        return wapiServicePort.getServices(clientId)
    }

    fun categoryNamesByIds(categoryIds: MutableList<Long>) =
        wapiCategoryPort.categoryNamesByIds(categoryIds)

    fun clientIdByKey(key: String) =
        wapiAccountPort.getClientIdByUserKey(key)
}