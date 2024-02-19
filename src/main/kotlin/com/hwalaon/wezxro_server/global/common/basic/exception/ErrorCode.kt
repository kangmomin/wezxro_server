package com.hwalaon.wezxro_server.global.common.basic.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val msg: String,
    val status: HttpStatus,
    val code: String
) {
    FORBIDDEN_ERROR("접근 권한이 없습니다.", HttpStatus.FORBIDDEN, "0002"),
    UNAUTHORIZED_ERROR("로그인이 필요한 작업입니다.", HttpStatus.UNAUTHORIZED, "0001"),
    UNEXPECTED_ERROR("서버에서 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR, "500"),
    PAGE_NOT_FOUND("엔드포인트를 찾지 못했습니다.", HttpStatus.NOT_FOUND, "404"),

    NOT_ENOUGH_DATA_ERROR("필수 요청 데이터가 정상적으로 입력되지 않았습니다.", HttpStatus.BAD_REQUEST, "4001"),
    NON_BODY_ERROR("데이터가 정상적으로 들어오지 않았습니다.", HttpStatus.BAD_REQUEST, "4002"),

    ACCOUNT_ALREADY_JOINED_ERROR("해당 이메일은 이미 가입이 완료되었습니다.", HttpStatus.CONFLICT, "A209"),
    ACCOUNT_NOT_FOUND_ERROR("계정을 찾을 수 없습니다.", HttpStatus.NO_CONTENT, "A204"),
    // Account Login - 계정 관련이라 A
    REFRESH_TOKEN_NOT_FOUND("로그인 정보를 찾을 수 없습니다.", HttpStatus.NO_CONTENT, "AL204"),

    CATEGORY_NOT_FOUND_ERROR("카테고리를 찾을 수 없습니다.", HttpStatus.NO_CONTENT, "C204"),
    
    SERVICE_CONFLICT_ERROR("이미 존재하는 서비스 입니다", HttpStatus.CONFLICT, "S209"),

    // MC - Master Client(Master 단의 C이기에)
    CLIENT_CONFLICT_ERROR("이미 존재하는 클라이언트입니다.", HttpStatus.CONFLICT, "MC209"),

    // TK - ToKen
    TOKEN_NOT_VALID_ERROR("토큰 값이 정상적이지 않습니다.", HttpStatus.BAD_REQUEST, "TK400"),
}