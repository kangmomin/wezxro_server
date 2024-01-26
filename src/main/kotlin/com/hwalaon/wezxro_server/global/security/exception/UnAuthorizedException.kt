package com.hwalaon.wezxro_server.global.security.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class UnAuthorizedException(
    val code: ErrorCode = ErrorCode.UNAUTHORIZED_ERROR
): BasicException(code)