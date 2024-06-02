package com.hwalaon.wezxro_server.domain.category.persistence.adapter

import com.hwalaon.wezxro_server.domain.category.persistence.repository.CategoryRepository
import com.hwalaon.wezxro_server.domain.service.persistence.port.ServiceCategoryPort
import org.springframework.stereotype.Component

@Component
class CategoryAdapter(
    private val categoryRepository: CategoryRepository
): ServiceCategoryPort {
    override fun categoryByDeactive(categoryIds: List<Long>): List<Long> =
        categoryRepository.findAllByIdInAndStatus(categoryIds).map {
            it.id!!
        }
}