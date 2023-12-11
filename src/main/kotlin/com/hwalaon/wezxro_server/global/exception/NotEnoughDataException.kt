package com.hwalaon.wezxro_server.global.exception

class NotEnoughDataException(val code: ErrorCode = ErrorCode.NOT_ENOUGH_DATA_ERROR): BasicException(code)