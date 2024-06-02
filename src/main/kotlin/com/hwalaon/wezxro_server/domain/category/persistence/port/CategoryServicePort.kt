package com.hwalaon.wezxro_server.domain.category.persistence.port

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface CategoryServicePort {
    fun updateStatusByCategoryId(categoryId: Long, clientId: UUID, status: BasicStatus)
}
