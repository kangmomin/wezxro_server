package com.hwalaon.wezxro_server.domain.account.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class AccountNotFoundException(
    val code: ErrorCode = ErrorCode.ACCOUNT_NOT_FOUND_ERROR
): BasicException(code)