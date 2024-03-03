package com.hwalaon.wezxro_server.domain.account.persistence.repository

import com.hwalaon.wezxro_server.domain.account.persistence.entity.AccountEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountEntityRepository: JpaRepository<AccountEntity, Long> {

    fun findOneByEmailAndClientIdAndStatusNot(email: String, clientId: UUID, status: BasicStatus = BasicStatus.DELETED): AccountEntity?
    fun findAllByClientIdAndStatusNot(clientId: UUID, status: BasicStatus = BasicStatus.DELETED): List<AccountEntity>
    fun findAllByUserIdAndStatusNot(userId: Long, status: BasicStatus = BasicStatus.DELETED): AccountEntity?

    fun findByUserIdAndClientIdAndStatusNot(userId: Long, clientId: UUID, status: BasicStatus = BasicStatus.DELETED): AccountEntity?
}