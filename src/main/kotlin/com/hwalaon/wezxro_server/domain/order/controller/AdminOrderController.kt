package com.hwalaon.wezxro_server.domain.order.controller

import com.hwalaon.wezxro_server.domain.order.service.CommandOrderService
import com.hwalaon.wezxro_server.domain.order.service.QueryOrderService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/o")
class AdminOrderController(
    private val queryOrderService: QueryOrderService,
    private val commandOrderService: CommandOrderService
) {

    @GetMapping("/list")
    fun getOrderList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ) = BasicResponse.ok(
            queryOrderService.getEveryOrderList(principalDetails.account.clientId!!)
        )
}