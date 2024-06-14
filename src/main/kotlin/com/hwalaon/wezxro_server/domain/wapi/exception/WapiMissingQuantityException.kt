package com.hwalaon.wezxro_server.domain.wapi.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class WapiMissingQuantityException(override val errorCode: ErrorCode = ErrorCode.MISSING_QUANTITY) : BasicException(errorCode) {

}
