package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.SendMailRequest
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountListResponse
import com.hwalaon.wezxro_server.domain.account.controller.response.CustomRateInfoResponse
import com.hwalaon.wezxro_server.domain.account.controller.response.StaticRateResponse
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.exception.DemoAccountCantUseException
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistence
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.client.exception.ClientNotFoundException
import com.hwalaon.wezxro_server.global.security.jwt.JwtGenerator
import com.hwalaon.wezxro_server.global.security.jwt.dto.TokenDto
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


@ReadOnlyService
class QueryAccountService(
    private val accountPersistence: AccountPersistence,
    private val jwtGenerator: JwtGenerator,
    @Value("\${spring.mail.username}")
    private val senderEmail: String,
    @Value("\${secret-key}")
    private val secretKey: String
) {

    fun detail(userInfo: PrincipalDetails) =
        accountPersistence.findById(userInfo.account.userId ?: 0, userInfo.account.clientId!!)
            ?: throw AccountNotFoundException()

    fun adminDetail(id: Long, clientId: UUID) =
        accountPersistence.findById(id, clientId) ?: throw AccountNotFoundException()

    fun list(clientId: UUID?): AccountListResponse {
        val accountList = accountPersistence.list(clientId!!).map {
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
        accountPersistence.findById(userId, clientId!!).let {
            if (it == null) throw AccountNotFoundException()
            StaticRateResponse.fromDomain(it)
        }

    fun sendMail(sendMailRequest: SendMailRequest, clientId: UUID) {
        val clientEmailInfo = accountPersistence.getEmailInfo(clientId)
            ?: throw ClientNotFoundException()

        val mailer = getJavaMailSender(
            clientEmailInfo.email,
            decrypt(clientEmailInfo.password))

        val simpleMailMessage = SimpleMailMessage()

        simpleMailMessage.setTo(sendMailRequest.email!!)
        simpleMailMessage.subject = sendMailRequest.subject!!
        simpleMailMessage.text = sendMailRequest.description!!
        simpleMailMessage.from = senderEmail

        mailer.send(simpleMailMessage)
    }
    private fun decrypt(value: String): String {
        val key: SecretKey = SecretKeySpec(secretKey.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedValue = Base64.getDecoder().decode(value)
        val decryptedValue = cipher.doFinal(decodedValue)
        return String(decryptedValue)
    }

    private fun getJavaMailSender(email: String, password: String): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smpt.${email.split("@")[1]}"
        mailSender.port = 587

        mailSender.username = email
        mailSender.password = password

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "true"

        return mailSender
    }

    fun demoLogin(key: UUID): TokenDto {
        val account = accountPersistence.getDemoAccount(key) ?: throw DemoAccountCantUseException()
        return jwtGenerator.generate(account.userId!!)
    }

    fun viewUserLogin(userId: Long, clientId: UUID): TokenDto {
        val account = accountPersistence.findById(userId, clientId) ?: throw AccountNotFoundException()

        return jwtGenerator.generate(account.userId!!)
    }

    fun customRateByUserId(clientId: UUID, userId: Long): List<CustomRateInfoResponse> {
        val crs = accountPersistence.getCustomRate(clientId, userId)

        return crs.toList()
    }
}