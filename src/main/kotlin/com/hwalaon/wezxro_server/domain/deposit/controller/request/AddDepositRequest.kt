package com.hwalaon.wezxro_server.domain.deposit.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.hwalaon.wezxro_server.domain.deposit.model.Deposit
import com.hwalaon.wezxro_server.domain.deposit.model.constant.DepositType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

class AddDepositRequest (
    @field: NotNull(message = "결제 금액은 비어있을 수 없습니다.")
    @field: Min(message = "충전은 1000원부터 가능합니다.", value = 1000)
    @JsonProperty("payamount")
    var amount: Long?,

    @field: NotEmpty(message = "예금주 명이 비어있습니다.")
    @JsonProperty("payname")
    var name: String?,

    @JsonProperty("business_email")
    var businessEmail: String?,
    @JsonProperty("business_phone")
    var businessPhone: String?,
    @JsonProperty("business_owner")
    var businessOwner: String?,
    @JsonProperty("business_name")
    var businessName: String?,
    @JsonProperty("business_regno")
    var businessRegno: String?,
    @JsonProperty("personal_phone")
    var personalPhone: String?,
) {
    fun toDomain() =
        Deposit(
            id = null,
            amount = amount,
            name = name,
            businessEmail = businessEmail,
            businessRegno = businessRegno,
            businessName = businessName,
            businessOwner = businessOwner,
            businessPhone = businessPhone,
            personalPhone = personalPhone,
            type = null,
            status = DepositType.PENDING,
            note = null,
            clientId = null,
            userId = null
        )
}