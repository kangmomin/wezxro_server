package com.hwalaon.wezxro_server.domain.category.exception

import com.hwalaon.wezxro_server.global.exception.BasicException
import com.hwalaon.wezxro_server.global.exception.ErrorCode

class CategoryNotFoundException(override val errorCode: ErrorCode = ErrorCode.CATEGORY_NOT_FOUND_ERROR) : BasicException(errorCode = errorCode)