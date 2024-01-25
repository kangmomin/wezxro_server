package com.hwalaon.wezxro_server.domain.service.persistence.repository

import com.hwalaon.wezxro_server.domain.service.persistence.entity.ServiceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceRepository: JpaRepository<ServiceEntity, Int> {
}