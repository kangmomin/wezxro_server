package com.hwalaon.wezxro_server.domain.category.persistence.repository

import com.hwalaon.wezxro_server.domain.category.persistence.entity.QCategoryEntity
import com.hwalaon.wezxro_server.domain.category.persistence.entity.QCategoryEntity.categoryEntity
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.dto.QWapiCategoryDto
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.dto.WapiCategoryDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CustomCategoryRepository(
    private val query: JPAQueryFactory
) {

    fun categoryNamesById(ids: List<Long>): List<WapiCategoryDto> =
        query.select(QWapiCategoryDto(categoryEntity.name!!, categoryEntity.id!!))
            .from(categoryEntity)
            .where(categoryEntity.id.`in`(ids))
            .fetch() ?: listOf()
}