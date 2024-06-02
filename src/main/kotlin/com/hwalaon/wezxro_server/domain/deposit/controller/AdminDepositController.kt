package com.hwalaon.wezxro_server.domain.deposit.controller

import com.hwalaon.wezxro_server.domain.deposit.controller.response.DepositListResponse
import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import com.hwalaon.wezxro_server.domain.deposit.service.CommandDepositService
import com.hwalaon.wezxro_server.domain.deposit.service.QueryDepositService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/admin/d")
class AdminDepositController(
    private val commandDepositService: CommandDepositService,
    private val queryDepositService: QueryDepositService,
) {

    @GetMapping("/list")
    fun depositList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestParam(name="status", required=false) status: DepositType?
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val pendingList = queryDepositService.list(principalDetails.account.clientId!!, status)

        val response = pendingList.map {
            DepositListResponse(
                depositId = it.id?.toString() ?: "대기중인 충전입니다.",
                name = it.name!!,
                amount = it.amount!!,
                note = it.note ?: "",
                status = it.status!!,
                updatedAt = it.updatedAt ?: LocalDateTime.now()
            )
        }

        return BasicResponse.ok(response)
    }
}