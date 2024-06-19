package com.hwalaon.wezxro_server.domain.account.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class CantSendMailException(override val errorCode: ErrorCode = ErrorCode.CANT_SEND_MAIL) : BasicException(errorCode) {

}
