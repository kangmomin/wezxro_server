package com.hwalaon.wezxro_server.domain.category.persistence.repository

import com.hwalaon.wezxro_server.domain.category.persistence.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<CategoryEntity, Long> {

    fun findAllByOrderBySort(): List<CategoryEntity>
}