package com.hwalaon.wezxro_server.domain.account.model.constant

enum class AccountStatus(
    val code: Int
) {
    ACTIVE(1),
    DEACTIVE(0),
    DELETED(999)
}
