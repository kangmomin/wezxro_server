package com.hwalaon.wezxro_server.global.common.client.service

import com.hwalaon.wezxro_server.global.annotation.CommandService
import com.hwalaon.wezxro_server.global.common.client.controller.request.AddClientRequest
import com.hwalaon.wezxro_server.global.common.client.exception.ClientConflictException
import com.hwalaon.wezxro_server.global.common.client.model.Client
import com.hwalaon.wezxro_server.global.common.client.persistence.ClientPersistence
import org.springframework.beans.factory.annotation.Value
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@CommandService
class ClientService(
    private val clientPersistence: ClientPersistence,
    @Value("\${secret-key}")
    private val secretKey: String
) {

    fun addClient(request: AddClientRequest): UUID? {
        if (!clientPersistence.validClient(request.domain!!, request.email!!))
            throw ClientConflictException()

        val encryptedPassword = encrypt(request.password!!)

        val clientEntity = Client(request.domain, request.email, encryptedPassword, request.host)
        return clientPersistence.save(clientEntity)
    }
    private fun encrypt(value: String): String {
        val key: SecretKey = SecretKeySpec(secretKey.toByteArray(), "AES")

        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedValue = cipher.doFinal(value.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedValue)
    }
}