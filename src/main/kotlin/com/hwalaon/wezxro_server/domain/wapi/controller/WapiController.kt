package com.hwalaon.wezxro_server.domain.wapi.controller

import com.hwalaon.wezxro_server.domain.wapi.controller.request.WapiAddOrderRequest
import com.hwalaon.wezxro_server.domain.wapi.controller.response.*
import com.hwalaon.wezxro_server.domain.wapi.exception.*
import com.hwalaon.wezxro_server.domain.wapi.service.CommandWapiService
import com.hwalaon.wezxro_server.domain.wapi.service.QueryWapiService
import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2")
class WapiController(
    private val commandWapiService: CommandWapiService,
    private val queryWapiService: QueryWapiService
) {

    @PostMapping
    fun handleRequest(
        @RequestParam(required = false) key: String?,
        @RequestParam(required = false) action: String?,
        @ModelAttribute orderData: WapiAddOrderRequest?,
        @RequestParam(required = false) order: Int?,
        @RequestParam(required = false) orders: String?,
        @RequestParam(required = false) refill: Int?,
        @RequestParam(required = false) refills: String?
    ): ResponseEntity<out Any> {
        if (key.isNullOrBlank()) return BasicResponse.wapiError("Invalid API key")
        if (action.isNullOrBlank()) return BasicResponse.wapiError("Incorrect Request")

        return try {
            when (action) {
                "services" -> getServiceList(key)
                "add" -> addOrder(key, orderData)
                "balance" -> getUserBalance(key)
                "status" -> getMultipleOrderStatus(key, orders)

//                ---------------- 24 06 15 --------------
//                "status" -> if (orders != null) getMultipleOrderStatus(key, orders) else getOrderStatus(key, order!!)
//                우리 사이트에선 안쓰일 API
//
//                "refill" -> if (orders != null) createMultipleRefill(key, orders) else createRefill(key, order!!)
//                "refill_status" -> if (refills != null) getMultipleRefillStatus(key, refills) else getRefillStatus(
//                    key,
//                    refill!!
//                )

                "cancel" -> createCancel(key, orders)
                else -> BasicResponse.wapiError("Invalid action parameter")
            }
        } catch (e: BasicException) {
            BasicResponse.wapiError(e.errorCode.msg)
        } catch (e: Exception) {
            e.printStackTrace()
            BasicResponse.wapiError("Incorrect Request")
        }
    }

    fun getServiceList(key: String): ResponseEntity<List<ServiceResponse>> {
        val services = queryWapiService.services(key)

        // 서비스 리스트를 반환하는 로직 구현
        return ResponseEntity.ok(services)
    }

    fun addOrder(
        key: String,
        orderData: WapiAddOrderRequest?
    ): ResponseEntity<AddOrderResponse> {
        if (orderData == null) throw Exception()
        if (orderData.link.isNullOrBlank()) throw WapiMissingLinkException()
        if (orderData.quantity == null) throw WapiMissingQuantityException()
        if (orderData.service == null) throw WapiInvalidServiceIdException()

        val orderId = commandWapiService.addOrder(key, orderData)

        // 주문 추가 로직 구현
        return ResponseEntity.ok(AddOrderResponse(order = orderId))
    }

    fun getOrderStatus(key: String, order: Int): ResponseEntity<OrderStatusResponse> {
        // 주문 상태 확인 로직 구현
        return ResponseEntity.ok(
            OrderStatusResponse(0.27819, 3572, "Partial", "157", "USD")
        )
    }

    fun getMultipleOrderStatus(key: String, ordersString: String?): ResponseEntity<Map<String, Any>> {
        if (ordersString.isNullOrBlank()) throw WapiOrderIdNotFoundException()

        val orders = ordersString.split(",").map {
            it.trim().toLong()
        }.sortedDescending()
        val orderStatus = queryWapiService.orderStatus(key, orders)

        // 여러 주문 상태 확인 로직 구현
        val response = HashMap<String, OrderStatusResponse>()

        orders.mapIndexed { idx, it ->
            response.put(it.toString(), orderStatus[idx])
        }

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

    fun createCancel(key: String, order: String?): ResponseEntity<Map<String, String>> {
        if (order == null) throw WapiBasicException()

        commandWapiService.cancelOrder(
            order.toLongOrNull()
            ?: throw WapiBasicException(), key)

        return ResponseEntity.ok(
            mapOf("success" to "Your order will be cancel asap. Thank you for patience."))
    }

    fun getUserBalance(key: String): ResponseEntity<BalanceResponse> {
        val balance = queryWapiService.userBalance(key)

        // 사용자 잔액 확인 로직 구현
        return ResponseEntity.ok(
            BalanceResponse(balance.toString(), "₩")
        )
    }
}