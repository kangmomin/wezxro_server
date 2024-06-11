package com.hwalaon.wezxro_server.domain.wapi.controller

import com.hwalaon.wezxro_server.domain.wapi.controller.response.*
import com.hwalaon.wezxro_server.domain.wapi.service.WapiService
import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2")
class WapiController(
    private val wapiService: WapiService
) {

    @PostMapping
    fun handleRequest(
        @RequestParam key: String,
        @RequestParam action: String,
        @RequestParam(required = false) service: Int?,
        @RequestParam(required = false) link: String?,
        @RequestParam(required = false) quantity: Int?,
        @RequestParam(required = false) runs: Int?,
        @RequestParam(required = false) interval: Int?,
        @RequestParam(required = false) order: Int?,
        @RequestParam(required = false) orders: String?,
        @RequestParam(required = false) refill: Int?,
        @RequestParam(required = false) refills: String?
    ): ResponseEntity<out Any> {
        return try {
            when (action) {
                "services" -> getServiceList(key)
                "add" -> addOrder(key, service!!, link!!, quantity!!, runs, interval)
                "status" -> if (orders != null) getMultipleOrderStatus(key, orders) else getOrderStatus(key, order!!)
                "refill" -> if (orders != null) createMultipleRefill(key, orders) else createRefill(key, order!!)
                "refill_status" -> if (refills != null) getMultipleRefillStatus(key, refills) else getRefillStatus(key, refill!!)
                "cancel" -> createCancel(key, orders!!)
                "balance" -> getUserBalance(key)
                else -> ResponseEntity.badRequest().body("Invalid action parameter")
            }
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Incorrect Request")
        }
    }

    fun getServiceList(key: String): ResponseEntity<Any> {
        return try {
            val services = wapiService.services(key)

            // 서비스 리스트를 반환하는 로직 구현
            ResponseEntity.ok(services)
        } catch (e: BasicException) {
            ResponseEntity.badRequest()
                .body(WapiErrorResponse(e.errorCode.msg))
        }
    }

    fun addOrder(
        key: String,
        service: Int,
        link: String,
        quantity: Int,
        runs: Int?,
        interval: Int?
    ): ResponseEntity<AddOrderResponse> {
        // 주문 추가 로직 구현
        return ResponseEntity.ok(AddOrderResponse(order = 23501))
    }

    fun getOrderStatus(key: String, order: Int): ResponseEntity<OrderStatusResponse> {
        // 주문 상태 확인 로직 구현
        return ResponseEntity.ok(
            OrderStatusResponse("0.27819", "3572", "Partial", "157", "USD")
        )
    }

    fun getMultipleOrderStatus(key: String, orders: String): ResponseEntity<Map<String, Any>> {
        // 여러 주문 상태 확인 로직 구현
        val response = mapOf(
            "1" to OrderStatusResponse("0.27819", "3572", "Partial", "157", "USD"),
            "10" to mapOf("error" to "Incorrect order ID"),
            "100" to OrderStatusResponse("1.44219", "234", "In progress", "10", "USD")
        )
        return ResponseEntity.ok(response)
    }

    fun createRefill(key: String, order: Int): ResponseEntity<RefillResponse> {
        // 리필 생성 로직 구현
        return ResponseEntity.ok(RefillResponse(refill = "1"))
    }

    fun createMultipleRefill(key: String, orders: String): ResponseEntity<List<MultiRefillResponse>> {
        // 여러 리필 생성 로직 구현
        val response = listOf(
            MultiRefillResponse(order = 1, refill = 1),
            MultiRefillResponse(order = 2, refill = 2),
            MultiRefillResponse(order = 3, refill = mapOf("error" to "Incorrect order ID"))
        )
        return ResponseEntity.ok(response)
    }

    fun getRefillStatus(key: String, refill: Int): ResponseEntity<RefillStatusResponse> {
        // 리필 상태 확인 로직 구현
        return ResponseEntity.ok(RefillStatusResponse(status = "Completed"))
    }

    fun getMultipleRefillStatus(key: String, refills: String): ResponseEntity<List<MultiRefillStatusResponse>> {
        // 여러 리필 상태 확인 로직 구현
        val response = listOf(
            MultiRefillStatusResponse(refill = 1, status = "Completed"),
            MultiRefillStatusResponse(refill = 2, status = "Rejected"),
            MultiRefillStatusResponse(refill = 3, status = mapOf("error" to "Refill not found"))
        )
        return ResponseEntity.ok(response)
    }

    fun createCancel(key: String, orders: String): ResponseEntity<List<CancelResponse>> {
        // 주문 취소 로직 구현
        val response = listOf(
            CancelResponse(order = 9, cancel = mapOf("error" to "Incorrect order ID")),
            CancelResponse(order = 2, cancel = 1)
        )
        return ResponseEntity.ok(response)
    }

    fun getUserBalance(key: String): ResponseEntity<BalanceResponse> {
        // 사용자 잔액 확인 로직 구현
        return ResponseEntity.ok(BalanceResponse(balance = "100.84292", currency = "USD"))
    }
}