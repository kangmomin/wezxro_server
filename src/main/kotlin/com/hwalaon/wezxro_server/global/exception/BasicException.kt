package com.hwalaon.wezxro_server.global.exception

open class BasicException(
    private val errorCode: ErrorCode
): RuntimeException()