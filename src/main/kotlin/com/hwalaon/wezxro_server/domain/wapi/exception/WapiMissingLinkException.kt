package com.hwalaon.wezxro_server.domain.wapi.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class WapiMissingLinkException(override val errorCode: ErrorCode = ErrorCode.MISSING_LINK) : BasicException(errorCode) {

}
