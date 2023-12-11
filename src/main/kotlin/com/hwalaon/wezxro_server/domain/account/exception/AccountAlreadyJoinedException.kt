package com.hwalaon.wezxro_server.domain.account.exception

import com.hwalaon.wezxro_server.global.exception.BasicException
import com.hwalaon.wezxro_server.global.exception.ErrorCode

class AccountAlreadyJoinedException(code: ErrorCode = ErrorCode.ACCOUNT_ALREADY_JOINED_ERROR) : BasicException(code)