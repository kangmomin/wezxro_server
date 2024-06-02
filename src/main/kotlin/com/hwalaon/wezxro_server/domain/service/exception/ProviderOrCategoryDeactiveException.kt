package com.hwalaon.wezxro_server.domain.service.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class ProviderOrCategoryDeactiveException(override val errorCode: ErrorCode = ErrorCode.SERVICE_CANT_ACTIVE) : BasicException(errorCode) {

}
