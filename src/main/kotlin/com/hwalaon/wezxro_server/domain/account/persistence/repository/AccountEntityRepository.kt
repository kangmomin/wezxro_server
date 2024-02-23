package com.hwalaon.wezxro_server.domain.account.persistence.repository

import com.hwalaon.wezxro_server.domain.account.persistence.entity.AccountEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountEntityRepository: JpaRepository<AccountEntity, Int> {

    fun findOneByEmailAndClientId(email: String, clientId: UUID): AccountEntity?
    fun findAllByClientIdAndStatusNot(clientId: UUID, status: BasicStatus): List<AccountEntity>
}