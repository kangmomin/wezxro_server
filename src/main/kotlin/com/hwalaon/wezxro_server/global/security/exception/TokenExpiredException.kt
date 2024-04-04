package com.hwalaon.wezxro_server.global.security.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class TokenExpiredException(
    override val errorCode: ErrorCode = ErrorCode.JWT_EXPIRED_ERROR,
    override val req: HttpServletRequest,
    override val res: HttpServletResponse
): BasicSecurityException(errorCode, res, req)