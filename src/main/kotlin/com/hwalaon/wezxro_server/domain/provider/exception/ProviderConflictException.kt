package com.hwalaon.wezxro_server.domain.provider.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class ProviderConflictException(override val errorCode: ErrorCode = ErrorCode.PROVIDER_CONFLICT_ERROR) : BasicException(errorCode) {

}
