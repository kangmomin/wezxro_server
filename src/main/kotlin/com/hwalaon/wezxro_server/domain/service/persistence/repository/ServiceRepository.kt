package com.hwalaon.wezxro_server.domain.service.persistence.repository

import com.hwalaon.wezxro_server.domain.service.persistence.entity.ServiceEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ServiceRepository: JpaRepository<ServiceEntity, Int> {

    fun findAllByClientIdAndCategoryIdAndStatusNotOrderById(clientId: UUID, categoryId: Long, status: BasicStatus = BasicStatus.DELETED): List<ServiceEntity>
    fun findAllByClientIdAndCategoryIdAndStatusNot(clientId: UUID, categoryId: Long, status: BasicStatus = BasicStatus.DELETED): List<ServiceEntity>
    fun findAllByClientIdAndStatusNotOrderById(clientId: UUID, status: BasicStatus = BasicStatus.DELETED): List<ServiceEntity>
    fun findByClientIdAndId(clientId: UUID, id: Long): ServiceEntity?
    fun findAllByClientIdAndProviderId(clientId: UUID, providerId: Long): MutableList<ServiceEntity>
    fun findAllByClientIdAndCategoryId(clientId: UUID, categoryId: Long): MutableList<ServiceEntity>
    fun findByClientId(clientId: UUID): MutableList<ServiceEntity>
}