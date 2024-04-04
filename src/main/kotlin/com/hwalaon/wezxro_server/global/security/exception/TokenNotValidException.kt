package com.hwalaon.wezxro_server.global.security.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class TokenNotValidException(
    override val errorCode: ErrorCode = ErrorCode.TOKEN_NOT_VALID_ERROR,
    override val res: HttpServletResponse,
    override val req: HttpServletRequest
) : BasicSecurityException(errorCode, res, req)
