package com.hwalaon.wezxro_server.domain.order.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class NotEnoughMoneyException(errorCode: ErrorCode = ErrorCode.NOT_ENOUGH_MONEY) : BasicException(errorCode) {

}
