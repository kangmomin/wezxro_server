package com.hwalaon.wezxro_server.domain.category.service

import com.hwalaon.wezxro_server.domain.category.persistence.CategoryPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService

@ReadOnlyService
class QueryCategoryService(
    private val categoryPersistenceAdapter: CategoryPersistenceAdapter
) {

    fun categoryList() = categoryPersistenceAdapter.findAll()
}