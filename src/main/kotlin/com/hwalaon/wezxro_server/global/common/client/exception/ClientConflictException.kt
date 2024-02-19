package com.hwalaon.wezxro_server.global.common.client.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class ClientConflictException(override val errorCode: ErrorCode = ErrorCode.CLIENT_CONFLICT_ERROR) : BasicException(errorCode)
