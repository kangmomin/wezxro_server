package com.hwalaon.wezxro_server.domain.account.persistence.repository

import com.hwalaon.wezxro_server.domain.account.persistence.entity.IpEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IpRepository: JpaRepository<IpEntity, Long> {
}