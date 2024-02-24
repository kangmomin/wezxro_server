package com.hwalaon.wezxro_server.domain.service.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class ServiceNotFoundException(override val errorCode: ErrorCode = ErrorCode.SERVICE_NOT_FOUND_ERROR): BasicException(errorCode) {
}