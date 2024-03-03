package com.hwalaon.wezxro_server.global.common.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class ApiRequestFailedException(override val errorCode: ErrorCode = ErrorCode.API_REQUEST_FAILED_ERROR) : BasicException(errorCode) {

}
