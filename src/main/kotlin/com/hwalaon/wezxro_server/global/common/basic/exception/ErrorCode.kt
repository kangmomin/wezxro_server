package com.hwalaon.wezxro_server.global.common.basic.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    var msg: String,
    val status: HttpStatus,
    val code: String
) {
    // 범용 error code
    FORBIDDEN_ERROR("접근 권한이 없습니다.", HttpStatus.FORBIDDEN, "0002"),
    UNAUTHORIZED_ERROR("로그인이 필요한 작업입니다.", HttpStatus.UNAUTHORIZED, "0001"),
    UNEXPECTED_ERROR("서버에서 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR, "500"),
    PAGE_NOT_FOUND("엔드포인트를 찾지 못했습니다.", HttpStatus.NOT_FOUND, "404"),
    NOT_ENOUGH_DATA_ERROR("필수 요청 데이터가 정상적으로 입력되지 않았습니다.", HttpStatus.BAD_REQUEST, "4001"),
    METHOD_MISS_MATCH_ERROR("메소드 타입이 틀리게 들어왔습니다.", HttpStatus.BAD_REQUEST, "4002"),
    NOT_SUPPORT_TYPE_REQUEST("지원하지 않는 콘텐츠 타입입니다.", HttpStatus.BAD_REQUEST, "4003"),
    VALIDATION_FAILED_ERROR("데이터 검증에 실패하였습니다.", HttpStatus.BAD_REQUEST, "4004"),
    WRONG_BODY_ERROR("데이터가 정상적으로 들어오지 않았습니다.", HttpStatus.BAD_REQUEST, "4005"),
    REQUEST_REJECTION_ERROR("요청이 거부되었습니다.", HttpStatus.BAD_REQUEST, "4006"),
    METHOD_NOT_SUPPORT_ERROR("지원하지 않는 메소드입니다.", HttpStatus.BAD_REQUEST, "4007"),

    // account
    ACCOUNT_ALREADY_JOINED_ERROR("해당 이메일은 이미 가입이 완료되었습니다.", HttpStatus.CONFLICT, "A209"),
    ACCOUNT_NOT_FOUND_ERROR("계정을 찾을 수 없습니다.", HttpStatus.OK, "A204"),

    // order
    NOT_ENOUGH_MONEY("보유액이 부족합니다.", HttpStatus.BAD_REQUEST, "O4001"),
    ORDER_COUNT_NOT_VALID("주문 수량이 초과하거나 부족합니다.", HttpStatus.BAD_REQUEST, "O4002"),

    // Account Login - 계정 관련이라 A
    REFRESH_TOKEN_NOT_FOUND("로그인 정보를 찾을 수 없습니다.", HttpStatus.OK, "AL204"),

    // category
    CATEGORY_NOT_FOUND_ERROR("카테고리를 찾을 수 없습니다.", HttpStatus.OK, "C204"),
    CATEGORY_CONFLICT_ERROR("이미 존재하는 카테고리입니다.", HttpStatus.CONFLICT, "C209"),


    SERVICE_CONFLICT_ERROR("이미 존재하는 서비스 입니다.", HttpStatus.CONFLICT, "S209"),
    SERVICE_NOT_FOUND_ERROR("서비스를 찾을 수 없습니다.", HttpStatus.OK, "S204"),

    // MC - Master Client(Master 단의 C이기에)
    CLIENT_CONFLICT_ERROR("이미 존재하는 클라이언트입니다.", HttpStatus.CONFLICT, "MC209"),

    // TK - ToKen
    TOKEN_NOT_VALID_ERROR("토큰 값이 정상적이지 않습니다.", HttpStatus.BAD_REQUEST, "TK400"),
    JWT_EXPIRED_ERROR("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED, "TK401"),

    // provider(api)
    API_REQUEST_FAILED_ERROR("도매처와의 연결을 실패하였습니다.", HttpStatus.BAD_REQUEST, "P5001"),
    PROVIDER_NOT_FOUND_ERROR("도매처를 찾을 수 없습니다.", HttpStatus.OK, "P204"),
    PROVIDER_CONFLICT_ERROR("이미 존재하는 도매처입니다.", HttpStatus.CONFLICT, "P209"),
    PROVIDER_SERVER_ERROR("도매처 서버에서 에러가 발생하였습니다.", HttpStatus.BAD_REQUEST, "P5002"),

    // Deposit
    DEPOSIT_CONFLICT_ERROR("동일한 정보의 충전 신청이 존재합니다.", HttpStatus.CONFLICT, "D209"),
    DEPOSIT_NOT_FOUND_ERROR("존재하지 않는 충전 신청입니다.", HttpStatus.OK, "D204"),
}