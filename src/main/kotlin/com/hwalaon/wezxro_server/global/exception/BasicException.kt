package com.hwalaon.wezxro_server.global.exception

open class BasicException(
    open val errorCode: ErrorCode
): RuntimeException()