package com.hwalaon.wezxro_server.domain.deposit.controller

import com.hwalaon.wezxro_server.domain.deposit.controller.request.AddDepositRequest
import com.hwalaon.wezxro_server.domain.deposit.service.CommandDepositService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.exception.UnAuthorizedException
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/d")
class DepositController(
    private val commandDepositService: CommandDepositService
) {

    @PostMapping("/add")
    fun addDeposit(
        @RequestBody @Valid addDepositRequest: AddDepositRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val deposit = addDepositRequest.toDomain()
        deposit.clientId = principalDetails.account.clientId
        deposit.userId = principalDetails.account.userId ?: throw UnAuthorizedException()
        commandDepositService.save(deposit)

        return BasicResponse.ok("충전 신청이 완료되었습니다.")
    }
}