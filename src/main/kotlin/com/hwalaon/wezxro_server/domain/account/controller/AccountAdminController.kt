package com.hwalaon.wezxro_server.domain.account.controller

import com.hwalaon.wezxro_server.domain.account.controller.request.AddCustomRateRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.UpdateAccountRequest
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
@RequestMapping("/u/admin")
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
        @PathVariable("id") id: Int,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) =
        commandAccountService.deleteAccount(id, principalDetails.account.clientId!!).run {
            BasicResponse.ok("삭제되었습니다.")
        }

    @PostMapping("/detail/{id}")
    fun accountDetails(
        @PathVariable("id") id: Int,
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
        @PathVariable userId: Int
    ) = BasicResponse.ok(queryAccountService.getStaticRate(principalDetails.account.clientId, userId))

    @PostMapping("/custom-rate")
    fun addCustomRate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody addCustomRateRequest: AddCustomRateRequest
    ) = commandAccountService.storeCustomRate(principalDetails.account.clientId!!, addCustomRateRequest).run {
        BasicResponse.ok("개별 감가액의 업데이트가 정상적으로 처리 되었습니다.")
    }
}