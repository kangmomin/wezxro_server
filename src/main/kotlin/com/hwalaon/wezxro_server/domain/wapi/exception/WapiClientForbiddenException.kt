package com.hwalaon.wezxro_server.domain.wapi.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class WapiClientForbiddenException(override val errorCode: ErrorCode = ErrorCode.CLIENT_FORBIDDEN) : BasicException(errorCode) {

}
