package com.hwalaon.wezxro_server.global.security.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class TokenExpiredException(override val errorCode: ErrorCode = ErrorCode.JWT_EXPIRED_ERROR): BasicException(errorCode)