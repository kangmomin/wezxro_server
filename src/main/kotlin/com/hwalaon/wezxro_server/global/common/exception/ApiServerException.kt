package com.hwalaon.wezxro_server.global.common.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class ApiServerException(
    override val errorCode: ErrorCode = ErrorCode.PROVIDER_SERVER_ERROR,
): BasicException(errorCode) {
    constructor(
        message: String = ErrorCode.PROVIDER_SERVER_ERROR.msg
    ) : this(ErrorCode.PROVIDER_SERVER_ERROR) {
        this.errorCode.msg = message
    }
}
