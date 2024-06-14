package com.hwalaon.wezxro_server.domain.wapi.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class WapiOrderIdNotFoundException(override val errorCode: ErrorCode = ErrorCode.WAPI_ORDER_ID_NOT_FOUND) : BasicException(errorCode) {

}
