package com.hwalaon.wezxro_server.domain.category.persistence

import com.hwalaon.wezxro_server.domain.category.exception.CategoryNotFoundException
import com.hwalaon.wezxro_server.domain.category.mapper.CategoryMapper
import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.domain.category.persistence.repository.CategoryRepository
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class CategoryPersistenceAdapter(
    private val categoryRepository: CategoryRepository,
    private val categoryMapper: CategoryMapper
) {

    /** sort로 정렬한 카테고리들 반환 */
    fun findAll(clientId: UUID) =
        categoryRepository.findAllByClientIdAndStatusOrderBySort(clientId, BasicStatus.ACTIVE).map {
            categoryMapper.toDomain(it)
        }
    fun findAllAdmin(clientId: UUID) =
        categoryRepository.findAllByClientIdAndStatusNotOrderBySort(clientId).map {
            categoryMapper.toDomain(it)
        }

    fun save(category: Category) =
          categoryMapper.toDomain(
              categoryRepository.save(
                  categoryMapper.toEntity(category)
              ))

    fun update(id: Long, category: Category) =
        categoryRepository.findByIdOrNull(id).let {
            if (it == null) throw CategoryNotFoundException()
            it.update(category)
        }

    fun delete(id: Long) {
        val category = categoryRepository.findByIdOrNull(id)
            ?: throw CategoryNotFoundException()
        category.status = BasicStatus.DELETED
    }

    fun validCategory(category: Category, clientId: UUID) =
        categoryRepository.existsByNameAndSortAndClientIdAndStatusNot(
            category.name!!, category.sort!!, clientId)

    fun detail(categoryId: Long, clientId: UUID?): Category? =
        categoryRepository.findByIdAndClientIdAndStatus(categoryId, clientId).let {
            if (it == null) return null
            categoryMapper.toDomain(it)
        }

    fun detailAdmin(categoryId: Long, clientId: UUID?): Category? =
        categoryRepository.findByIdAndClientIdAndStatusNot(categoryId, clientId).let {
            if (it == null) return null
            categoryMapper.toDomain(it)
        }
}