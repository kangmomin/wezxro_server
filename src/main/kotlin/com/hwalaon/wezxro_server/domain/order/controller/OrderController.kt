package com.hwalaon.wezxro_server.domain.order.controller

import com.hwalaon.wezxro_server.domain.order.controller.request.AddOrderRequest
import com.hwalaon.wezxro_server.domain.order.controller.response.AddOrderResponse
import com.hwalaon.wezxro_server.domain.order.service.CommandOrderService
import com.hwalaon.wezxro_server.domain.order.service.QueryOrderService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(name = "/o")
class OrderController(
    private val queryOrderService: QueryOrderService,
    private val commandOrderService: CommandOrderService,
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

    @PostMapping("/add")
    suspend fun addOrder(
        @RequestBody @Valid addOrderRequest: AddOrderRequest,
        @AuthenticationPrincipal principal: PrincipalDetails,
    ) = BasicResponse.ok(
        commandOrderService.addOrder(
            addOrderRequest,
            principal.account.clientId!!,
            principal.account.money!!,
            principal.account.userId!!).let {
                AddOrderResponse(it)
        }
    )

}