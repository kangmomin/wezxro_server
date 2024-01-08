package com.hwalaon.wezxro_server.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val msg: String,
    val code: HttpStatus
) {
    FORBIDDEN_ERROR("접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    UNAUTHORIZED_ERROR("로그인이 필요한 작업입니다.", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NOT_FOUND_ERROR("계정을 찾을 수 없습니다.", HttpStatus.NO_CONTENT),
    UNEXPECTED_ERROR("서버에서 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NON_BODY_ERROR("데이터가 정상적으로 들어오지 않았습니다.", HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_DATA_ERROR("필수 요청 데이터가 정상적으로 입력되지 않았습니다.", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_JOINED_ERROR("해당 이메일은 이미 가입이 완료되었습니다.", HttpStatus.CONFLICT),
    REFRESH_TOKEN_NOT_FOUND("로그인 정보를 찾을 수 없습니다.", HttpStatus.NO_CONTENT),
}