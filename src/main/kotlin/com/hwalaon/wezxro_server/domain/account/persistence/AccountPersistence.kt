package com.hwalaon.wezxro_server.domain.account.persistence

import com.hwalaon.wezxro_server.domain.account.controller.request.AddCustomRateRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.UpdateStatusRequest
import com.hwalaon.wezxro_server.domain.account.controller.response.CustomRateInfoResponse
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.persistence.mapper.AccountMapper
import com.hwalaon.wezxro_server.domain.account.persistence.mapper.CustomRateMapper
import com.hwalaon.wezxro_server.domain.account.persistence.repository.AccountEntityRepository
import com.hwalaon.wezxro_server.domain.account.persistence.customRepository.CustomAccountRepository
import com.hwalaon.wezxro_server.domain.account.persistence.customRepository.CustomCustomRepository
import com.hwalaon.wezxro_server.domain.account.persistence.entity.IpEntity
import com.hwalaon.wezxro_server.domain.account.persistence.port.AccountClientPort
import com.hwalaon.wezxro_server.domain.account.persistence.port.AccountServicePort
import com.hwalaon.wezxro_server.domain.account.persistence.port.dto.ServiceRateInfoDto
import com.hwalaon.wezxro_server.domain.account.persistence.repository.IpRepository
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountPersistence(
    private val accountEntityRepository: AccountEntityRepository,
    private val accountMapper: AccountMapper,
    private val customAccountRepository: CustomAccountRepository,
    private val customCustomRepository: CustomCustomRepository,
    private val customRateMapper: CustomRateMapper,
    private val accountServicePort: AccountServicePort,
    private val ipRepository: IpRepository,
    private val clientPort: AccountClientPort,
) {
    fun login(loginRequest: LoginRequest) =
        accountEntityRepository.findOneByEmailAndClientIdAndStatusNot(
            loginRequest.email!!,
            loginRequest.key!!
        ).let {
            if (it == null) throw AccountNotFoundException()
            accountMapper.toDomain(it)
        }

    fun loginLog(userId: Long, ip: String) {
        ipRepository.save(IpEntity(
            id = null,
            userId = userId,
            ip = ip
        ))
    }

    fun join(account: Account) =
        accountMapper.toEntity(account).let {
            accountEntityRepository.save(it)
        }

    fun isExistAccount(email: String, clientId: UUID) = customAccountRepository.isExistEmail(email, clientId)
    fun isExistName(name: String, clientId: UUID) = customAccountRepository.isExistName(name, clientId)

    fun isExistAccount(email: String, clientId: UUID, userId: Long) =
        customAccountRepository.isExistAccountForUpdate(email, clientId, userId)

    fun updateInfo(account: Account): Result<Account> {
        val accountEntity =
            accountEntityRepository.findByUserIdAndClientIdAndStatusNot(account.userId!!, account.clientId!!)
            ?: return Result.failure(Error("not found"))

        accountEntity.email = account.email
        accountEntity.name = account.name
        accountEntity.status = account.status

        val accountDomain = accountMapper.toDomain(accountEntity)
        return Result.success(accountDomain)
    }

    fun findById(id: Long, clientId: UUID): Account? {
        return accountMapper.toDomain(
            accountEntityRepository.findByUserIdAndClientIdAndStatusNot(id, clientId)
                ?: return null)
    }

    fun list(clientId: UUID) =
        accountEntityRepository
            .findAllByClientIdAndStatusNotOrderByUserId(clientId).map {
                accountMapper.toDomain(it)
            }

    fun storeCustomRate(userId: Long, clientId: UUID, addCustomRateRequest: AddCustomRateRequest) =
        accountEntityRepository.findByUserIdAndClientIdAndStatusNot(userId, clientId).let { account ->
            account ?: throw AccountNotFoundException()

            val ids = addCustomRateRequest.customRates!!.map { it.crId }
            account.customRate?.removeIf { cr -> cr.id !in ids }

            addCustomRateRequest.customRates.forEach {addCustomRate ->
                if (addCustomRate.crId == null || addCustomRate.crId == 0L) {
                    account.addCustomRate(addCustomRate)
                    return@forEach
                }

                if (account.customRate == null) account.customRate = mutableListOf()

                account.customRate?.forEach { cr ->
                    if (cr.id == addCustomRate.crId) cr.rate = addCustomRate.rate ?: 0.0
                }
            }
        }

    fun addMoney(userId: Long, amount: Double, clientId: UUID): Double? {
        val user = accountEntityRepository.findByUserIdAndClientIdAndStatusNot(userId, clientId) ?: return null

        user.money = user.money!! + amount

        return user.money
    }

    fun getPassword(userId: Long, clientId: UUID): String? {
        val account = accountEntityRepository.findByUserIdAndClientIdAndStatusNot(userId, clientId) ?: return null

        return account.password
    }

    fun setMoney(userId: Long, amount: Double, clientId: UUID): String? {
        val account = accountEntityRepository.findByUserIdAndClientIdAndStatusNot(userId, clientId) ?: return null

        account.money = amount

        return ""
    }

    fun setPassword(encodedPassword: String, userId: Long, clientId: UUID): String? {
        val account = accountEntityRepository.findByUserIdAndClientIdAndStatusNot(userId, clientId) ?: return null

        account.password = encodedPassword

        return account.password
    }

    fun updateStatus(updateStatusRequest: UpdateStatusRequest, clientId: UUID): String? {
        val account =
            accountEntityRepository.findByUserIdAndClientIdAndStatusNot(updateStatusRequest.userId!!, clientId) ?: return null

        account.status = updateStatusRequest.status!!

        return ""
    }

    fun getDemoAccount(key: UUID): Account? {
        accountEntityRepository.findByClientIdAndStatusAndRole(key).let {
            if (it == null) return null

            return accountMapper.toDomain(it)
        }
    }

    fun getCustomRate(clientId: UUID, userId: Long): MutableList<CustomRateInfoResponse> {
        var customRates = customCustomRepository.getCustomRates(userId, clientId)

        val serviceIds = customRates.map {
            it.serviceId
        }
        val serviceInfos = accountServicePort.serviceInfo(serviceIds)

        customRates = customRates.map { cr ->
            var serviceInfo: ServiceRateInfoDto? = null

            serviceInfos.forEach() { sr ->
                if (cr.serviceId == sr.serviceId) {
                    serviceInfo = sr
                }
            }

            cr.originalRate = serviceInfo?.originalRate
            cr.serviceName = serviceInfo?.serviceName

            return@map cr
        }.toMutableList()

        return customRates
    }

    fun updateKey(userId: Long, key: String): Result<String> {
        val user = accountEntityRepository.findByIdOrNull(userId) ?: return Result.failure(Error("not found"))

        if (user.status == BasicStatus.DELETED) return Result.failure(Error("not found"))

        user.key = key

        return Result.success(key)
    }

    /**
     * if it's valid, it returns true
     * otherwise false
     */
    fun validKey(key: String): Boolean? {
        return accountEntityRepository.findByKey(key) == null
    }

    fun getEmailInfo(clientId: UUID) = clientPort.getClientEmailInfo(clientId)
    fun exportList(clientId: UUID) = customAccountRepository.exportList(clientId)
}