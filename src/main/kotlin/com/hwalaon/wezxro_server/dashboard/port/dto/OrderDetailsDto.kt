package com.hwalaon.wezxro_server.dashboard.port.dto

data class OrderDetailsDto (
    /**
     * 사용한 전체 금액
     */
    val totalCharge: Double,
    /**
     * 주문의 총량
     */
    val orderCount: Long,

    val statusCount: Long
) {
}