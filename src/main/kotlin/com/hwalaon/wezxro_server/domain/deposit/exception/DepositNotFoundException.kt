package com.hwalaon.wezxro_server.domain.deposit.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class DepositNotFoundException(override val errorCode: ErrorCode = ErrorCode.DEPOSIT_NOT_FOUND_ERROR) : BasicException(errorCode) {

}
