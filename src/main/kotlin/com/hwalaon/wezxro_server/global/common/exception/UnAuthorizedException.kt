package com.hwalaon.wezxro_server.global.common.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class UnAuthorizedException(
    override val errorCode: ErrorCode = ErrorCode.UNAUTHORIZED_ERROR
): BasicException(errorCode)