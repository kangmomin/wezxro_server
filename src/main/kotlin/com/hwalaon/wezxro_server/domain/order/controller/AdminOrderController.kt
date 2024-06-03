package com.hwalaon.wezxro_server.domain.order.controller

import com.hwalaon.wezxro_server.domain.order.controller.request.UpdateOrderRequest
import com.hwalaon.wezxro_server.domain.order.service.CommandOrderService
import com.hwalaon.wezxro_server.domain.order.service.QueryOrderService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

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

    @PatchMapping("/update")
    fun updateOrder(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody @Valid updateOrderRequest: UpdateOrderRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandOrderService.updateOrder(updateOrderRequest)

        return BasicResponse.ok("주문을 수정하였습니다.")
    }

    @PatchMapping("/cancel/{orderId}")
    fun cancelOrder(
        @PathVariable orderId: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandOrderService.cancelOrder(orderId, principalDetails.account.clientId!!)
        return BasicResponse.ok("주문을 취소하였습니다.")
    }
}