package com.hwalaon.wezxro_server.global.security.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

open class BasicSecurityException (
    open val errorCode: ErrorCode,
    open val res: HttpServletResponse,
    open val req: HttpServletRequest
): RuntimeException()