package com.hwalaon.wezxro_server.domain.account.controller

import com.hwalaon.wezxro_server.domain.account.controller.request.JoinRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.UpdateAccountRequest
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountDetailResponse
import com.hwalaon.wezxro_server.domain.account.service.CommandAccountService
import com.hwalaon.wezxro_server.domain.account.service.QueryAccountService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/u")
class AccountController(
    private val queryAccountService: QueryAccountService,
    private val commandAccountService: CommandAccountService,
) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid loginRequest: LoginRequest) =
        queryAccountService.login(loginRequest)

    @PostMapping("/join")
    fun join(@RequestBody @Valid joinRequest: JoinRequest) =
        commandAccountService.join(joinRequest)

    @PatchMapping("/update")
    fun updateInfo(@RequestBody @Valid updateAccountRequest: UpdateAccountRequest) =
        commandAccountService.updateAccountInfo(updateAccountRequest.toDomain())

    @PostMapping("/delete/{id}")
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