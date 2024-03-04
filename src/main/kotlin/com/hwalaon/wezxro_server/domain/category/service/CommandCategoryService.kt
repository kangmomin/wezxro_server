package com.hwalaon.wezxro_server.domain.category.service

import com.hwalaon.wezxro_server.domain.category.exception.CategoryConflictException
import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.domain.category.persistence.CategoryPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.CommandService
import java.util.*

@CommandService
class CommandCategoryService(
    private val categoryPersistenceAdapter: CategoryPersistenceAdapter
) {

    fun addCategory(category: Category, clientId: UUID) =
        categoryPersistenceAdapter.validCategory(category, clientId).let {
            if (it) throw CategoryConflictException()
            category.clientId = clientId
            categoryPersistenceAdapter.save(category)
        }

    fun updateCategory(categoryId: Long, category: Category) =
        categoryPersistenceAdapter.update(categoryId, category)

    fun delete(categoryId: Long) = categoryPersistenceAdapter.delete(categoryId)
}