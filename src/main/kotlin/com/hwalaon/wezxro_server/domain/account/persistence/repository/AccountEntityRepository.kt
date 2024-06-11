package com.hwalaon.wezxro_server.domain.account.persistence.repository

import com.hwalaon.wezxro_server.domain.account.model.constant.AccountRole
import com.hwalaon.wezxro_server.domain.account.persistence.entity.AccountEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountEntityRepository: JpaRepository<AccountEntity, Long> {

    fun findOneByEmailAndClientIdAndStatusNot(email: String, clientId: UUID, status: BasicStatus = BasicStatus.DELETED): AccountEntity?
    fun findAllByClientIdAndStatusNotOrderByUserId(clientId: UUID, status: BasicStatus = BasicStatus.DELETED): List<AccountEntity>
    fun findAllByUserIdAndStatusNot(userId: Long, status: BasicStatus = BasicStatus.DELETED): AccountEntity?

    fun findByUserIdAndClientIdAndStatusNot(userId: Long, clientId: UUID, status: BasicStatus = BasicStatus.DELETED): AccountEntity?

    /** 데모 계정 가져오기 */
    fun findByClientIdAndStatusAndRole(clientId: UUID, status: BasicStatus = BasicStatus.ACTIVE, role: AccountRole = AccountRole.DEMO): AccountEntity?

    fun findByKey(key: String): AccountEntity?
}