package com.hwalaon.wezxro_server.domain.account.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class AccountAlreadyJoinedException(code: ErrorCode = ErrorCode.ACCOUNT_ALREADY_JOINED_ERROR) : BasicException(code)