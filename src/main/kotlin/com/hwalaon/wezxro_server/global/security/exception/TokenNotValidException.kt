package com.hwalaon.wezxro_server.global.security.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class TokenNotValidException(override val errorCode: ErrorCode = ErrorCode.TOKEN_NOT_VALID_ERROR) : BasicException(errorCode) {

}
