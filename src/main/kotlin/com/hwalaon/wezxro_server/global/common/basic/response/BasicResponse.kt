package com.hwalaon.wezxro_server.global.common.basic.response

import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity

data class BasicResponse<T> (
    val data: T,
    val status: Int,
) {

    companion object {
        fun error(errorInfo: ErrorCode) = ResponseEntity
            .status(errorInfo.code)
            .body(MsgResponse(errorInfo.msg))

        fun ok(data: Any, headers: HttpHeaders?) = ResponseEntity
            .status(200)
            .headers(headers)
            .body(data)
        fun ok(data: Any) = ResponseEntity
            .status(200)
            .body(data)

        fun okMsg(msg: String) = ResponseEntity
            .status(200)
            .body(MsgResponse(msg))

        fun created(data: Any?) = ResponseEntity
            .status(201)
            .body(data)
    }
}