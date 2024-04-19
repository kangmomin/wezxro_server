package com.hwalaon.wezxro_server.domain.deposit.controller.request

import kotlinx.serialization.Serializable

@Serializable
data class CheckPayRequest(
    val regPkey: String?,
    val ugrd: String?,
    val BnakName: String?,
    val MNO: String?,
    val rtpayData: String?
)