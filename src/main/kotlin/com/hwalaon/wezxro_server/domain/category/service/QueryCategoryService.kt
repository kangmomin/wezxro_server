package com.hwalaon.wezxro_server.domain.category.service

import com.hwalaon.wezxro_server.domain.category.controller.response.CategoryListResponse
import com.hwalaon.wezxro_server.domain.category.exception.CategoryNotFoundException
import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.domain.category.persistence.CategoryPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import java.util.*

@ReadOnlyService
class QueryCategoryService(
    private val categoryPersistenceAdapter: CategoryPersistenceAdapter
) {

    fun categoryList(clientId: UUID) =
        categoryPersistenceAdapter.findAll(clientId).map {
            CategoryListResponse(it.id!!, it.name!!, it.sort!!, it.status!!.code)
        }

    fun categoryListForAdmin(clientId: UUID) =
        categoryPersistenceAdapter.findAllAdmin(clientId).map {
            CategoryListResponse(it.id!!, it.name!!, it.sort!!, it.status!!.code)
        }

    fun detail(clientId: UUID?, categoryId: Long, isAdmin: Boolean = false): Category {
        val category = if (isAdmin) categoryPersistenceAdapter.detailAdmin(categoryId, clientId)
                        else categoryPersistenceAdapter.detail(categoryId, clientId)

        if (category == null) throw CategoryNotFoundException()

        return category
    }
}