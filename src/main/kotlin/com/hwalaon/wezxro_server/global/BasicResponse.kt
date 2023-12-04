package com.hwalaon.wezxro_server.global

import com.hwalaon.wezxro_server.global.exception.ErrorCode
import com.hwalaon.wezxro_server.global.exception.dto.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity

data class BasicResponse<T> (
    val data: T,
    val status: Int,
) {

    companion object {
        fun error(errorInfo: ErrorCode) = ResponseEntity
            .status(errorInfo.code)
            .body(ErrorResponse(errorInfo.msg))

        fun ok(data: Any, headers: HttpHeaders?) = ResponseEntity
            .status(200)
            .headers(headers)
            .body(data)

        fun created(data: Any?) = ResponseEntity
            .status(201)
            .body(data)
    }
}