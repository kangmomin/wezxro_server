package com.hwalaon.wezxro_server.domain.account.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class DemoAccountCantUseException(override val errorCode: ErrorCode = ErrorCode.DEMO_ACCOUNT_CANT_USE) : BasicException(errorCode) {

}
