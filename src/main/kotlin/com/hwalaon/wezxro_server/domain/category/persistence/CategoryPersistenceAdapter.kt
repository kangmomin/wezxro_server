package com.hwalaon.wezxro_server.domain.category.persistence

import com.hwalaon.wezxro_server.domain.category.persistence.repository.CategoryRepository
import org.springframework.stereotype.Component

@Component
class CategoryPersistenceAdapter(
    private val categoryRepository: CategoryRepository
) {

    /** sort로 정렬한 카테고리들 반환 */
    fun findAll() = categoryRepository.findAllByOrderBySort()

}