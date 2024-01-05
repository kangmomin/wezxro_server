package com.hwalaon.wezxro_server.domain.category.persistence

import com.hwalaon.wezxro_server.domain.category.mapper.CategoryMapper
import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.domain.category.persistence.repository.CategoryRepository
import org.springframework.stereotype.Component

@Component
class CategoryPersistenceAdapter(
    private val categoryRepository: CategoryRepository,
    private val categoryMapper: CategoryMapper
) {

    /** sort로 정렬한 카테고리들 반환 */
    fun findAll() = categoryRepository.findAllByOrderBySort()

    fun save(category: Category) =
          categoryRepository.save(
              categoryMapper.toEntity(category)
          )

}