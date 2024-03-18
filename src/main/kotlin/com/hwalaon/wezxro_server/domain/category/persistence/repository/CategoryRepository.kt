package com.hwalaon.wezxro_server.domain.category.persistence.repository

import com.hwalaon.wezxro_server.domain.category.persistence.entity.CategoryEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository: JpaRepository<CategoryEntity, Long> {

    fun findAllByClientIdAndStatusNotOrderBySort(clientId: UUID, status: BasicStatus = BasicStatus.DELETED): List<CategoryEntity>
    fun findAllByClientIdAndStatusOrderBySort(clientId: UUID, status: BasicStatus = BasicStatus.ACTIVE): List<CategoryEntity>
    fun existsByNameAndSortAndClientIdAndStatusNot(name: String, sort: Int, clientId: UUID, status: BasicStatus = BasicStatus.DELETED): Boolean
    fun findByIdAndClientIdAndStatusNot(categoryId: Long, clientId: UUID?, status: BasicStatus = BasicStatus.DELETED): CategoryEntity?
    fun findByIdAndClientIdAndStatus(categoryId: Long, clientId: UUID?, status: BasicStatus = BasicStatus.ACTIVE): CategoryEntity?
}