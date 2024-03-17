package com.hwalaon.wezxro_server.domain.deposit.controller

import com.hwalaon.wezxro_server.domain.deposit.service.CommandDepositService
import com.hwalaon.wezxro_server.domain.deposit.service.QueryDepositService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/d")
class AdminDepositController(
    private val commandDepositService: CommandDepositService,
    private val queryDepositService: QueryDepositService,
) {

    @GetMapping("/list")
    fun depositList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) =
        BasicResponse.ok(
            queryDepositService.pendingList(principalDetails.account.clientId!!))
}