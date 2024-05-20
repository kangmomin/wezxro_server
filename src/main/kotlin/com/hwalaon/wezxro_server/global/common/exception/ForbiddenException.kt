package com.hwalaon.wezxro_server.global.common.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class ForbiddenException(override val errorCode: ErrorCode = ErrorCode.FORBIDDEN_ERROR): BasicException(errorCode) {
}