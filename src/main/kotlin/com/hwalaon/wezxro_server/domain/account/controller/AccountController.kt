package com.hwalaon.wezxro_server.domain.account.controller

import com.hwalaon.wezxro_server.domain.account.controller.request.JoinRequest
import com.hwalaon.wezxro_server.domain.account.controller.request.LoginRequest
import com.hwalaon.wezxro_server.domain.account.service.JoinAccountService
import com.hwalaon.wezxro_server.domain.account.service.LoginAccountService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/u")
class AccountController(
    private val loginAccountService: LoginAccountService,
    private val joinAccountService: JoinAccountService,
) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid loginRequest: LoginRequest) =
        loginAccountService.execute(loginRequest)

    @PostMapping("/join")
    fun join(@RequestBody @Valid joinRequest: JoinRequest) =
        joinAccountService.execute(joinRequest)
}