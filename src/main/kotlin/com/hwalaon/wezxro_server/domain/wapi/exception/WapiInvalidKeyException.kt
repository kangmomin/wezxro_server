package com.hwalaon.wezxro_server.domain.wapi.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class WapiInvalidKeyException(override val errorCode: ErrorCode = ErrorCode.INVALID_KEY) : BasicException(errorCode) {

}
