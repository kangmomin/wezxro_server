package com.hwalaon.wezxro_server.domain.account.persistence.repository

import com.hwalaon.wezxro_server.domain.account.persistence.entity.AccountEntity
import com.hwalaon.wezxro_server.domain.account.persistence.repository.custom.AccountEntityRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountEntityRepository: JpaRepository<AccountEntity, Int>, AccountEntityRepositoryCustom {

    fun findOneByEmail(email: String): AccountEntity?
}