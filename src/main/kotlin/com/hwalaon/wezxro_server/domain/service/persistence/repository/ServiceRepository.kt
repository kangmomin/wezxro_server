package com.hwalaon.wezxro_server.domain.service.persistence.repository

import com.hwalaon.wezxro_server.domain.service.persistence.entity.ServiceEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ServiceRepository: JpaRepository<ServiceEntity, Int> {

    fun findAllByClientIdAndCategoryIdOrderById(clientId: UUID, categoryId: Int): List<ServiceEntity>
    fun findAllByClientIdOrderById(clientId: UUID): List<ServiceEntity>
}