package com.hwalaon.wezxro_server.domain.order.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class OrderNotFoundException(override val errorCode: ErrorCode = ErrorCode.ORDER_NOT_FOUND) : BasicException(errorCode)
