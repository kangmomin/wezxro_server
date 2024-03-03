package com.hwalaon.wezxro_server.domain.order.model.constant

enum class OrderStatus(
    string: String
) {
    COMPLETED("completed"),
    CANCELED("canceled"),
    PROCESSING("processing"),
    INPROGRESS("inprogress"),
    PARTIALS("partials"),
    PENDING("pending")
}
