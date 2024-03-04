package com.hwalaon.wezxro_server.domain.provider.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class ProviderNotFoundException(override val errorCode: ErrorCode = ErrorCode.PROVIDER_NOT_FOUND_ERROR): BasicException(errorCode)