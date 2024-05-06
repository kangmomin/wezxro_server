package com.hwalaon.wezxro_server.domain.account.controller

import com.hwalaon.wezxro_server.domain.account.controller.request.*
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountDetailResponse
import com.hwalaon.wezxro_server.domain.account.service.CommandAccountService
import com.hwalaon.wezxro_server.domain.account.service.QueryAccountService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/u")
class AccountAdminController(
    private val queryAccountService: QueryAccountService,
    private val commandAccountService: CommandAccountService,
) {

    @PatchMapping("/update")
    fun updateInfo(
        @RequestBody @Valid updateAccountRequest: UpdateAccountRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val account = updateAccountRequest.toDomain()
        account.clientId = principalDetails.account.clientId
        account.userId = principalDetails.account.userId

        commandAccountService.updateAccountInfo(account).run {
            return BasicResponse.ok("계정 정보를 성공적으로 변경하였습니다.")
        }
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) =
        commandAccountService.deleteAccount(id, principalDetails.account.clientId!!).run {
            BasicResponse.ok("삭제되었습니다.")
        }

    @PostMapping("/detail/{id}")
    fun accountDetails(
        @PathVariable("id") id: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) =
        BasicResponse.ok(
            AccountDetailResponse.fromDomain(
                queryAccountService.adminDetail(id, principalDetails.account.clientId!!)))

    @GetMapping("/list")
    fun accountList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) = BasicResponse.ok(queryAccountService.list(principalDetails.account.clientId))

    @GetMapping("/static-rate/{userId}")
    fun staticRate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable userId: Long
    ) = BasicResponse.ok(queryAccountService.getStaticRate(principalDetails.account.clientId, userId))

    @PatchMapping("/static-rate/update/{userId}")
    fun updateStaticRate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable userId: Long,
        @RequestBody @Valid updateStaticRate: UpdateStaticRate
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAccountService.updateStaticRate(
            updateStaticRate.staticRate!!, userId,
            principalDetails.account.clientId!!)

        return BasicResponse.ok("전체 감가액을 지정하였습니다.")
    }

    @PostMapping("/custom-rate")
    fun addCustomRate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody addCustomRateRequest: AddCustomRateRequest
    ) = commandAccountService.storeCustomRate(principalDetails.account.clientId!!, addCustomRateRequest).run {
        BasicResponse.ok("개별 감가액의 업데이트가 정상적으로 처리 되었습니다.")
    }

    @PatchMapping("/money/add")
    fun addMoney(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody @Valid updateMoneyRequest: UpdateMoneyRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAccountService.addFund(updateMoneyRequest,
            principalDetails.account.clientId!!,
            principalDetails.account.userId!!)

        return BasicResponse.ok("유저의 보유액이 추가되었습니다.")
    }
}