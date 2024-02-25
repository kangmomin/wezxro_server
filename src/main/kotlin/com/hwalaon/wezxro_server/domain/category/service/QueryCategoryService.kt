package com.hwalaon.wezxro_server.domain.category.service

import com.hwalaon.wezxro_server.domain.category.exception.CategoryNotFoundException
import com.hwalaon.wezxro_server.domain.category.persistence.CategoryPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import java.util.*

@ReadOnlyService
class QueryCategoryService(
    private val categoryPersistenceAdapter: CategoryPersistenceAdapter
) {

    fun categoryList(clientId: UUID) =
        categoryPersistenceAdapter.findAll(clientId)

    fun detail(clientId: UUID?, categoryId: Long) =
        categoryPersistenceAdapter.detail(categoryId, clientId).let {
            if (it == null) throw CategoryNotFoundException()
            return@let it
        }
}