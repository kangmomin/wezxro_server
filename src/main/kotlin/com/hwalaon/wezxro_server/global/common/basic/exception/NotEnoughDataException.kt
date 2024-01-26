package com.hwalaon.wezxro_server.global.common.basic.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class NotEnoughDataException(val code: ErrorCode = ErrorCode.NOT_ENOUGH_DATA_ERROR): BasicException(code)