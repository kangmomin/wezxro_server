package com.hwalaon.wezxro_server.global.security.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class SecurityUnAuthorizedException(
    val code: ErrorCode = ErrorCode.UNAUTHORIZED_ERROR,
    override val req: HttpServletRequest,
    override val res: HttpServletResponse
): BasicSecurityException(code, res, req)