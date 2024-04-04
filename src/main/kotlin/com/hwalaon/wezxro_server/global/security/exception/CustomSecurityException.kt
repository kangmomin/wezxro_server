package com.hwalaon.wezxro_server.global.security.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class CustomSecurityException(
    override val res: HttpServletResponse,
    override val req: HttpServletRequest,
    override val errorCode: ErrorCode
): BasicSecurityException(errorCode, res, req)