package com.hwalaon.wezxro_server.domain.account.controller

import com.hwalaon.wezxro_server.domain.account.controller.request.DemoLoginRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.JoinRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountDetailResponse
import com.hwalaon.wezxro_server.domain.account.controller.response.KeyResponse
import com.hwalaon.wezxro_server.domain.account.service.CommandAccountService
import com.hwalaon.wezxro_server.domain.account.service.QueryAccountService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
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
    fun login(
        @RequestBody @Valid loginRequest: LoginRequest,
        req: HttpServletRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val token = commandAccountService.login(loginRequest, req.remoteAddr)
        return BasicResponse.ok(token)
    }

    @PostMapping("/login/demo")
    fun demoLogin(
        @RequestBody @Valid demoLoginRequest: DemoLoginRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val tokenDto = queryAccountService.demoLogin(demoLoginRequest.key!!)

        return BasicResponse.ok(tokenDto)
    }

    @PostMapping("/join")
    fun join(@RequestBody @Valid joinRequest: JoinRequest): ResponseEntity<BasicResponse.BaseResponse> {
        commandAccountService.join(joinRequest)

        return BasicResponse.created("회원가입에 성공하였습니다.")
    }

    @GetMapping("/info")
    fun accountDetails(
        @AuthenticationPrincipal userInfo: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val accountDetail = queryAccountService.detail(userInfo)
        val response = AccountDetailResponse.fromDomain(accountDetail)

        return BasicResponse.ok(response)
    }

    @PostMapping("/key")
    fun generateKey(
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val key = commandAccountService.generateKey(principalDetails.account.userId!!)

        return BasicResponse.ok( KeyResponse(key) )
    }
}