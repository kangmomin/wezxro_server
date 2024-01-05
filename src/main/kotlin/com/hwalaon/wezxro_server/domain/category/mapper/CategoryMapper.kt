package com.hwalaon.wezxro_server.domain.category.mapper

import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.domain.category.persistence.entity.CategoryEntity
import com.hwalaon.wezxro_server.global.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class CategoryMapper: BasicMapper<Category, CategoryEntity> {
    override fun toDomain(entity: CategoryEntity) =
        entity.let {
            Category(
                name = it.name,
                status = it.status,
                id = it.id,
                sort = it.sort
            )
        }

    override fun toEntity(domain: Category) =
        domain.let {
            CategoryEntity(
                name = it.name,
                status = it.status,
                id = it.id,
                sort = it.sort
            )
        }
}