package com.hwalaon.wezxro_server.domain.account.persistence.repository

import com.hwalaon.wezxro_server.domain.account.persistence.entity.CustomRateEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CustomRateRepository: JpaRepository<CustomRateEntity, Long> {
    fun findAllByServiceId(serviceId: Long): MutableList<CustomRateEntity>
}