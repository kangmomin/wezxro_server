package com.hwalaon.wezxro_server.domain.category.service

import com.hwalaon.wezxro_server.domain.category.exception.CategoryConflictException
import com.hwalaon.wezxro_server.domain.category.exception.CategoryNotFoundException
import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.domain.category.persistence.CategoryPersistence
import com.hwalaon.wezxro_server.domain.category.persistence.port.CategoryServicePort
import com.hwalaon.wezxro_server.global.annotation.CommandService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.beans.factory.annotation.Qualifier
import java.util.*

@CommandService
class CommandCategoryService(
    private val categoryPersistence: CategoryPersistence,
    @Qualifier("categoryServicePort")
    private val servicePort: CategoryServicePort
) {

    fun addCategory(category: Category, clientId: UUID) =
        categoryPersistence.validCategory(category, clientId).let {
            if (it) throw CategoryConflictException()
            category.clientId = clientId
            categoryPersistence.save(category)
        }

    fun updateCategory(categoryId: Long, category: Category) {
        val validCategory = categoryPersistence.validCategory(category, category.clientId!!, categoryId)
        if (validCategory) throw CategoryConflictException()

        categoryPersistence.update(categoryId, category)
    }

    fun delete(categoryId: Long) = categoryPersistence.delete(categoryId)
    fun toggleStatus(categoryId: Long, clientId: UUID): BasicStatus {
        val category = categoryPersistence.detailAdmin(categoryId, clientId) ?: throw CategoryNotFoundException()

        category.status = if (category.status === BasicStatus.ACTIVE) BasicStatus.DEACTIVE else BasicStatus.ACTIVE

        categoryPersistence.update(categoryId, category)
        servicePort.updateStatusByCategoryId(categoryId, clientId, category.status!!)

        return category.status!!
    }

    fun updateSort(categoryId: Long, sort: Int, clientId: UUID) {
        val category = categoryPersistence.detailAdmin(categoryId, clientId) ?: throw CategoryNotFoundException()

        category.sort = sort

        categoryPersistence.update(categoryId, category)
    }
}