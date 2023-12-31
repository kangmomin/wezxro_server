package com.hwalaon.wezxro_server.domain.refreshToken.exception

import com.hwalaon.wezxro_server.global.exception.BasicException
import com.hwalaon.wezxro_server.global.exception.ErrorCode

class RefreshTokenNotFoundException(override val errorCode: ErrorCode = ErrorCode.REFRESH_TOKEN_NOT_FOUND) : BasicException(errorCode)
