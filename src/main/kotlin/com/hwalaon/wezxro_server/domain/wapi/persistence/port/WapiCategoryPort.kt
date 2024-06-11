package com.hwalaon.wezxro_server.domain.wapi.persistence.port

import com.hwalaon.wezxro_server.domain.wapi.persistence.port.dto.WapiCategoryDto
import org.springframework.stereotype.Component

@Component
interface WapiCategoryPort {
    fun categoryNamesByIds(ids: MutableList<Long>): MutableList<WapiCategoryDto>
}
