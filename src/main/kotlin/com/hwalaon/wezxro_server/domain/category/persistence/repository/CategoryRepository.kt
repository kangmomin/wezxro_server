package com.hwalaon.wezxro_server.domain.category.persistence.repository

import com.hwalaon.wezxro_server.domain.category.persistence.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository: JpaRepository<CategoryEntity, Long> {

    fun findAllByClientIdOrderBySort(clientId: UUID): List<CategoryEntity>
    fun existsByNameAndSortAndClientId(name: String, sort: Int, clientId: UUID): Boolean
}