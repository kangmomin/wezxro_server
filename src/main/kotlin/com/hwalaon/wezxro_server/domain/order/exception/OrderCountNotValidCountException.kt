package com.hwalaon.wezxro_server.domain.order.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class OrderCountNotValidCountException(override val errorCode: ErrorCode = ErrorCode.ORDER_COUNT_NOT_VALID) : BasicException(errorCode) {

}
