package com.hwalaon.wezxro_server.domain.category.service

import com.hwalaon.wezxro_server.domain.category.controller.response.CategoryListResponse
import com.hwalaon.wezxro_server.domain.category.exception.CategoryNotFoundException
import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.domain.category.persistence.CategoryPersistence
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import java.util.*

@ReadOnlyService
class QueryCategoryService(
    private val categoryPersistence: CategoryPersistence
) {

    fun categoryList(clientId: UUID) =
        categoryPersistence.findAll(clientId).map {
            CategoryListResponse(it.id!!, it.name!!, it.sort!!, it.status!!.code)
        }

    fun categoryListForAdmin(clientId: UUID) =
        categoryPersistence.findAllAdmin(clientId).map {
            CategoryListResponse(it.id!!, it.name!!, it.sort!!, it.status!!.code)
        }

    fun detail(clientId: UUID?, categoryId: Long, isAdmin: Boolean = false): Category {
        val category = if (isAdmin) categoryPersistence.detailAdmin(categoryId, clientId)
                        else categoryPersistence.detail(categoryId, clientId)

        if (category == null) throw CategoryNotFoundException()

        return category
    }
}