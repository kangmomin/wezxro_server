package com.hwalaon.wezxro_server.domain.provider.persistence.repository

import com.hwalaon.wezxro_server.domain.provider.persistence.entity.ProviderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProviderRepository: JpaRepository<ProviderEntity, Long>