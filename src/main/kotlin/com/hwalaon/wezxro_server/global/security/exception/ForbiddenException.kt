package com.hwalaon.wezxro_server.global.security.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class ForbiddenException(
    val code: ErrorCode = ErrorCode.FORBIDDEN_ERROR
) : BasicException(code)
