package com.hwalaon.wezxro_server.domain.category.persistence.adapter

import com.hwalaon.wezxro_server.domain.category.persistence.repository.CategoryRepository
import com.hwalaon.wezxro_server.domain.category.persistence.repository.CustomCategoryRepository
import com.hwalaon.wezxro_server.domain.service.persistence.port.ServiceCategoryPort
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.WapiCategoryPort
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.dto.WapiCategoryDto
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CategoryAdapter(
    private val categoryRepository: CategoryRepository,
    private val customCategoryRepository: CustomCategoryRepository
): ServiceCategoryPort, WapiCategoryPort {
    override fun categoryByDeactive(categoryIds: List<Long>): List<Long> =
        categoryRepository.findAllByIdInAndStatus(categoryIds).map {
            it.id!!
        }

    override fun isCategoryDeactive(categoryId: Long): Boolean =
        (categoryRepository.findByIdOrNull(categoryId)?.status ?: BasicStatus.DEACTIVE) == BasicStatus.DEACTIVE

    override fun categoryNamesByIds(ids: MutableList<Long>): MutableList<WapiCategoryDto> =
        customCategoryRepository.categoryNamesById(ids).toMutableList()
}