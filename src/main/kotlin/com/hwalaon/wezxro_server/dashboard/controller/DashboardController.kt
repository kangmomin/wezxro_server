package com.hwalaon.wezxro_server.dashboard.controller

import com.hwalaon.wezxro_server.dashboard.service.CommandDashboardService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DashboardController(
    private val dashboardService: CommandDashboardService
) {
    @GetMapping("/dashboard")
    fun userDashboard(
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) = BasicResponse.ok(
            dashboardService.userDashboard(principalDetails.account.userId!!)
        )
}