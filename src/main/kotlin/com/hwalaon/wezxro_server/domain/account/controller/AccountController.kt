package com.hwalaon.wezxro_server.domain.account.controller

import com.hwalaon.wezxro_server.domain.account.controller.request.DemoLoginRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.JoinRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountDetailResponse
import com.hwalaon.wezxro_server.domain.account.service.CommandAccountService
import com.hwalaon.wezxro_server.domain.account.service.QueryAccountService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.common.basic.response.MsgResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/u")
class
AccountController(
    private val queryAccountService: QueryAccountService,
    private val commandAccountService: CommandAccountService,
) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid loginRequest: LoginRequest) =
        BasicResponse.ok(queryAccountService.login(loginRequest))

    @PostMapping("/login/demo")
    fun demoLogin(
        @RequestBody @Valid demoLoginRequest: DemoLoginRequest
    ) = BasicResponse.ok(
        queryAccountService.demoLogin(demoLoginRequest.key!!))

    @PostMapping("/join")
    fun join(@RequestBody @Valid joinRequest: JoinRequest) {
        commandAccountService.join(joinRequest)

        BasicResponse.created("회원가입에 성공하였습니다.")
    }

    @GetMapping("/info")
    fun accountDetails(
        @AuthenticationPrincipal userInfo: PrincipalDetails) =
        BasicResponse.ok(
            AccountDetailResponse.fromDomain(
                queryAccountService.detail(userInfo)))

}