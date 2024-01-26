package com.hwalaon.wezxro_server.global.common.basic.exception

open class BasicException(
    open val errorCode: ErrorCode
): RuntimeException()