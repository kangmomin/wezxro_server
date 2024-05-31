package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.SendMailRequest
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountListResponse
import com.hwalaon.wezxro_server.domain.account.controller.response.CustomRateInfoResponse
import com.hwalaon.wezxro_server.domain.account.controller.response.StaticRateResponse
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.exception.DemoAccountCantUseException
import com.hwalaon.wezxro_server.domain.account.model.CustomRate
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.security.jwt.JwtGenerator
import com.hwalaon.wezxro_server.global.security.jwt.dto.TokenDto
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*


@ReadOnlyService
class QueryAccountService(
    private val accountPersistenceAdapter: AccountPersistenceAdapter,
    private val passwordEncoder: PasswordEncoder,
    private val jwtGenerator: JwtGenerator,
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    private val senderEmail: String,
) {

    fun login(loginRequest: LoginRequest): TokenDto {
        val account = accountPersistenceAdapter.login(loginRequest)

        if (passwordEncoder.matches(loginRequest.password, account.password).not() ||
            account.userId == null)
            throw AccountNotFoundException()

        return jwtGenerator.generate(account.userId!!)
    }

    fun detail(userInfo: PrincipalDetails) =
        accountPersistenceAdapter.findById(userInfo.account.userId ?: 0, userInfo.account.clientId!!)
            ?: throw AccountNotFoundException()

    fun adminDetail(id: Long, clientId: UUID) =
        accountPersistenceAdapter.findById(id, clientId) ?: throw AccountNotFoundException()

    fun list(clientId: UUID?): AccountListResponse {
        val accountList = accountPersistenceAdapter.list(clientId!!).map {
            AccountListResponse.AccountDto(
                userId = it.userId!!,
                name = it.name!!,
                email = it.email!!,
                status = it.status!!,
                role = it.role!!,
                createdAt = it.createdAt!!,
                staticRate = it.staticRate!!,
                money = it.money!!,
            )
        }
        var activateCnt: Long = 0
        var deactivateCnt: Long = 0

        accountList.forEach {
            if (it.status == BasicStatus.ACTIVE) ++activateCnt else ++deactivateCnt
        }

        return AccountListResponse(
            accountList = accountList,
            activateCnt = activateCnt,
            deactivateCnt = deactivateCnt,
            totalCnt = activateCnt + deactivateCnt
        )
    }

    fun getStaticRate(clientId: UUID?, userId: Long) =
        accountPersistenceAdapter.findById(userId, clientId!!).let {
            if (it == null) throw AccountNotFoundException()
            StaticRateResponse.fromDomain(it)
        }

    fun sendMail(sendMailRequest: SendMailRequest) {
        val mailSender = SimpleMailMessage()
        mailSender.setTo(sendMailRequest.email!!)
        mailSender.subject = sendMailRequest.subject!!
        mailSender.text = sendMailRequest.description!!
        mailSender.from = senderEmail
        javaMailSender.send(mailSender)
    }

    fun demoLogin(key: UUID): TokenDto {
        val account = accountPersistenceAdapter.getDemoAccount(key) ?: throw DemoAccountCantUseException()
        return jwtGenerator.generate(account.userId!!)
    }

    fun viewUserLogin(userId: Long, clientId: UUID): TokenDto {
        val account = accountPersistenceAdapter.findById(userId, clientId) ?: throw AccountNotFoundException()

        return jwtGenerator.generate(account.userId!!)
    }

    fun customRateByUserId(clientId: UUID, userId: Long): List<CustomRateInfoResponse> {
        val crs = accountPersistenceAdapter.getCustomRate(clientId, userId)

        return crs.toList()
    }
}