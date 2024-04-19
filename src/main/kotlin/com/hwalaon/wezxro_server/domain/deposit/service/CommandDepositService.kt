package com.hwalaon.wezxro_server.domain.deposit.service

import com.google.gson.Gson
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.deposit.controller.request.CheckPayRequest
import com.hwalaon.wezxro_server.domain.deposit.controller.response.CheckPayResponse
import com.hwalaon.wezxro_server.domain.deposit.exception.DepositConflictException
import com.hwalaon.wezxro_server.domain.deposit.model.Deposit
import com.hwalaon.wezxro_server.domain.deposit.persistence.DepositPersistence
import com.hwalaon.wezxro_server.domain.deposit.persistence.dto.CheckPayDto
import com.hwalaon.wezxro_server.global.annotation.CommandService
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value


@CommandService
class CommandDepositService(
    private val depositPersistence: DepositPersistence,

    @Value("\${rtpKey}")
    private val rtpKey: String
) {

    fun save(deposit: Deposit) {
        if (depositPersistence.conflictValid(deposit)) throw DepositConflictException()

        depositPersistence.save(deposit)
    }

    fun check(checkPayRequest: CheckPayRequest, requestUrl: String): CheckPayResponse {
        val response = CheckPayResponse("200", "OK")

        if (checkPayRequest.regPkey != rtpKey) {
            response.RCODE = "600"
            return response
        }

        try {
            val checkPayDto = getRTPay(rtpKey,
                if (checkPayRequest.ugrd == null) "https://rtpay.net/CheckPay/setPurl.php"
                else requestUrl,
                checkPayRequest)

            if (response.RCODE != "200") {
                response.PCHK = "NO"
            } else {
                val depositInfo = depositPersistence.updateDeposit(checkPayDto)
                if (depositInfo == null) response.PCHK = "NO"
                else depositPersistence.updateMoney(depositInfo.userId, depositInfo.money)
                        ?: throw AccountNotFoundException()
            }
        } catch (e: Exception) {
            response.RCODE = "600"
        }

        return response
    }

    fun getRTPay(
        RTP_KEY: String, requestUrl: String,
        checkPayRequest: CheckPayRequest
    ): CheckPayDto {
        val ugrd = checkPayRequest.ugrd
        var RTP_URL = "https://rtpay.net/CheckPay/checkpay.php"
        if ("11" == ugrd || "12" == ugrd) RTP_URL = "https://rtpay.net/CheckPay/test_checkpay.php"


        val requestBody = FormBody.Builder()
            .add("MNO", checkPayRequest.MNO ?: "")
            .add("BnakName", checkPayRequest.BnakName ?: "")
            .add("rtpayData", checkPayRequest.rtpayData ?: "")
            .add("ugrd", checkPayRequest.ugrd ?: "")
            .add("regPkey", checkPayRequest.regPkey ?: "")
            .build()

        val request = Request.Builder()
            .url(RTP_URL)
            .post(requestBody)
            .build()

        // HTTP 요청 보내기
        val response = OkHttpClient().newCall(request).execute()

        // 응답을 JSON으로 파싱하여 객체로 변환
        val responseBody = response.body?.string()
        val checkPayResponse = Gson().fromJson(responseBody, CheckPayDto::class.java)

        return checkPayResponse
    }
}