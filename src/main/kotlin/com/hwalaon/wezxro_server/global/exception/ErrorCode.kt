package com.hwalaon.wezxro_server.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val msg: String,
    val code: HttpStatus
) {
    FORBIDDEN_ERROR("접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    UNAUTHORIZED_ERROR("로그인이 필요한 작업입니다.", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NOT_FOUND_ERROR("계정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    UNEXPECTED_ERROR("서버에서 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
}