package com.hwalaon.wezxro_server.domain.account.controller.request

import org.jetbrains.annotations.NotNull

data class AddCustomRateRequest (
    @field:NotNull("유저 아이디는 필수 입력 값 입니다.")
    val userId: Int,

    @field:NotNull("개별 감가액은 빈 배열이라도 들어가있어야 합니다.")
    val customRates: MutableList<AddCustomRateList>

) {
    companion object {
        class AddCustomRateList (
            /**
             * customRateId
             */
            val crId: Long?,

            @field:NotNull("서비스 아이디는 필수 입력 값 입니다.")
            val serviceId: Long,

            val rate: Float?
        )
    }
}