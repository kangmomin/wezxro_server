package com.hwalaon.wezxro_server.domain.order.controller

import com.hwalaon.wezxro_server.domain.order.service.QueryOrderService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(name = "/o")
class OrderController(
    private val queryOrderService: QueryOrderService
) {

    @GetMapping("/list")
    fun getMyOrder(
        @AuthenticationPrincipal principal: PrincipalDetails
    ) =
        BasicResponse.ok(
            queryOrderService.getMyOrderList(
                principal.account.userId!!,
                principal.account.clientId!!)
        )
}