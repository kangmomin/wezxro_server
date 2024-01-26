package com.hwalaon.wezxro_server.domain.category.mapper

import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.domain.category.persistence.entity.CategoryEntity
import com.hwalaon.wezxro_server.global.common.basic.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class CategoryMapper: BasicMapper<Category, CategoryEntity> {
    override fun toDomain(entity: CategoryEntity) =
        Category(
            name = entity.name,
            status = entity.status,
            id = entity.id,
            sort = entity.sort
        ).let {
            it.createdAt = entity.createdAt
            it.updatedAt = entity.updatedAt
            it
        }

    override fun toEntity(domain: Category) =
        CategoryEntity(
            id = domain.id,
            name = domain.name,
            status = domain.status,
            sort = domain.sort
        ).let {
            it.createdAt = domain.createdAt
            it.updatedAt = domain.updatedAt
            it
        }
}