package com.hwalaon.wezxro_server.domain.account.persistence.repository

import com.hwalaon.wezxro_server.domain.account.persistence.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountEntityRepository: JpaRepository<AccountEntity, Int> {

    fun findOneByEmail(email: String): AccountEntity?
}