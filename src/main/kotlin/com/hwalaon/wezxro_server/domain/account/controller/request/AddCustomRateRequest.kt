package com.hwalaon.wezxro_server.domain.account.controller.request

import org.jetbrains.annotations.NotNull

data class AddCustomRateRequest (
    @NotNull
    val userId: Int,

    val customRates: MutableList<AddCustomRateList>

) {
    companion object {
        class AddCustomRateList (
            /**
             * customRateId
             */
            val crId: Long?,

            @NotNull
            val serviceId: Long,

            val rate: Float?
        )
    }
}