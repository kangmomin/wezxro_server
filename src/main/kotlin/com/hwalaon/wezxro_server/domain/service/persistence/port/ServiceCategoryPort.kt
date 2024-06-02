package com.hwalaon.wezxro_server.domain.service.persistence.port

import org.springframework.stereotype.Component

@Component
interface ServiceCategoryPort {
    fun categoryByDeactive(categoryIds: List<Long>): List<Long>
    fun isCategoryDeactive(categoryId: Long): Boolean
}
