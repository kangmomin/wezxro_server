package com.hwalaon.wezxro_server.domain.order.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class WapiCancelUnavailableException(override val errorCode: ErrorCode = ErrorCode.WAPI_CANT_CANCEL) : BasicException(errorCode) {

}
