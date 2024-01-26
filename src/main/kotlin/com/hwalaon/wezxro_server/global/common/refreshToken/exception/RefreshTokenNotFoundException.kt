package com.hwalaon.wezxro_server.global.common.refreshToken.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class RefreshTokenNotFoundException(override val errorCode: ErrorCode = ErrorCode.REFRESH_TOKEN_NOT_FOUND) : BasicException(errorCode)
