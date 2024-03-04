package com.hwalaon.wezxro_server.domain.category.exception

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode

class CategoryConflictException(override val errorCode: ErrorCode = ErrorCode.CATEGORY_CONFLICT_ERROR) : BasicException(errorCode) {

}
