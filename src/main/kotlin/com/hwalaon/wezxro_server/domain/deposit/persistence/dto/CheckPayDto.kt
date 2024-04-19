package com.hwalaon.wezxro_server.domain.deposit.persistence.dto

data class CheckPayDto(
    val RCODE: String,
    val RBANK: String?,
    val RNAME: String?,
    val RPAY: String?,
    val RTEXT: String?,
    val RMSG: String?
)