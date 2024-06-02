package com.hwalaon.wezxro_server.domain.deposit.controller

import com.hwalaon.wezxro_server.domain.deposit.controller.request.AddDepositRequest
import com.hwalaon.wezxro_server.domain.deposit.controller.request.CheckPayRequest
import com.hwalaon.wezxro_server.domain.deposit.controller.response.CheckPayResponse
import com.hwalaon.wezxro_server.domain.deposit.service.CommandDepositService
import com.hwalaon.wezxro_server.domain.deposit.service.QueryDepositService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.common.exception.UnAuthorizedException
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/d")
class DepositController(
    private val commandDepositService: CommandDepositService,
    private val queryDepositService: QueryDepositService
) {

    @PostMapping("/add")
    fun addDeposit(
        @RequestBody @Valid addDepositRequest: AddDepositRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val deposit = addDepositRequest.toDomain()
        deposit.clientId = principalDetails.account.clientId
        if (deposit.type == null) deposit.type = "DEFAULT"
        deposit.userId = principalDetails.account.userId ?: throw UnAuthorizedException()
        commandDepositService.save(deposit)

        return BasicResponse.ok("충전 신청이 완료되었습니다.")
    }

    @GetMapping("/list")
    fun listDeposit(
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val response = queryDepositService.depositListByUserId(principalDetails.account.userId!!)

        return BasicResponse.ok(response)
    }

    @PostMapping("/check")
    fun checkDeposit(
        @RequestParam("regPkey") regPkey: String?,
        @RequestParam("ugrd") ugrd: String?,
        @RequestParam("BnakName") bankName: String?,
        @RequestParam("MNO") mno: String?,
        @RequestParam("rtpayData") data: String?,
        request: HttpServletRequest
    ): ResponseEntity<CheckPayResponse> {
        val checkPayRequest = CheckPayRequest(regPkey, ugrd, bankName, mno, data)
        val response = commandDepositService.check(checkPayRequest, request.requestURI.toString())

        return ResponseEntity.ok(response)
    }
}