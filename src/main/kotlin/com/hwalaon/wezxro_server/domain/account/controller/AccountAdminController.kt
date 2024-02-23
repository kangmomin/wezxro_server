package com.hwalaon.wezxro_server.domain.account.controller

import com.hwalaon.wezxro_server.domain.account.controller.request.UpdateAccountRequest
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountDetailResponse
import com.hwalaon.wezxro_server.domain.account.service.CommandAccountService
import com.hwalaon.wezxro_server.domain.account.service.QueryAccountService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/u/admin")
class AccountAdminController(
    private val queryAccountService: QueryAccountService,
    private val commandAccountService: CommandAccountService,
) {

    @PatchMapping("/update")
    fun updateInfo(@RequestBody @Valid updateAccountRequest: UpdateAccountRequest) =
        commandAccountService.updateAccountInfo(updateAccountRequest.toDomain()).run {
            BasicResponse.ok("계정 정보를 성공적으로 변경하였습니다.")
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
}