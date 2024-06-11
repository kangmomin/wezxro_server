package com.hwalaon.wezxro_server.domain.order.controller

import com.hwalaon.wezxro_server.domain.order.controller.request.UpdateOrderRequest
import com.hwalaon.wezxro_server.domain.order.service.CommandOrderService
import com.hwalaon.wezxro_server.domain.order.service.QueryOrderService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/o")
class AdminOrderController(
    private val queryOrderService: QueryOrderService,
    private val commandOrderService: CommandOrderService
) {

    private val logger = LoggerFactory.getLogger(AdminOrderController::class.java)

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

        logger.info("Update: by - ${principalDetails.account.userId!!} / ${updateOrderRequest.orderId}")

        return BasicResponse.ok("주문을 수정하였습니다.")
    }

    @DeleteMapping("/delete/{orderId}")
    fun deleteOrder(
        @PathVariable orderId: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandOrderService.deleteOrder(orderId)

        logger.info("Delete: by - ${principalDetails.account.userId!!} / $orderId")

        return BasicResponse.ok("주문 정보를 삭제하였습니다.")
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