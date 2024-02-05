package com.hwalaon.wezxro_server.domain.account.controller

import com.hwalaon.wezxro_server.domain.account.controller.request.UpdateAccountRequest
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountDetailResponse
import com.hwalaon.wezxro_server.domain.account.service.CommandAccountService
import com.hwalaon.wezxro_server.domain.account.service.QueryAccountService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import jakarta.validation.Valid
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
            BasicResponse.okMsg("계정 정보를 성공적으로 변경하였습니다.")
        }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Int) =
        commandAccountService.deleteAccount(id).run {
            BasicResponse.okMsg("삭제되었습니다.")
        }


    @PostMapping("/detail/{id}")
    fun accountDetails(@PathVariable("id") id: Int) =
        BasicResponse.ok(
            AccountDetailResponse.fromDomain(
                queryAccountService.detail(id)))
}