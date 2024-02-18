package com.hwalaon.wezxro_server.global.common.basic.response

import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class BasicResponse<T> (
    val data: T,
    val status: Int,
) {

    companion object {
        fun error(errorInfo: ErrorCode) = ResponseEntity
            .status(errorInfo.status)
            .body(
                BaseResponse(
                    status = BaseStatus.ERROR,
                    data = BaseErrorResponse(
                        message = errorInfo.msg,
                        code = errorInfo.code
                    )
                ))

        fun ok(data: Any, headers: HttpHeaders?) =
            ResponseEntity
                .status(200)
                .headers(headers)
                .body(BaseResponse(
                    status = BaseStatus.SUCCESS,
                    data = data
                ))
        fun ok(data: Any) =
            ResponseEntity
                .status(200)
                .body(
                    BaseResponse(
                        status = BaseStatus.SUCCESS,
                        data = data
                    ))

        fun ok(msg: String) = ResponseEntity
            .status(200)
            .body(BaseResponse(
                status = BaseStatus.SUCCESS,
                data = MsgResponse(msg)
            ))

        fun customStatus(data: Any, httpStatus: HttpStatus, baseStatus: BaseStatus) = ResponseEntity
            .status(httpStatus)
            .body(BaseResponse(
                status = baseStatus,
                data = data
            ))

        fun customStatus(data: String, httpStatus: HttpStatus, baseStatus: BaseStatus) = ResponseEntity
            .status(httpStatus)
            .body(BaseResponse(
                status = baseStatus,
                data = MsgResponse(data)
            ))


        fun created(data: Any) = ResponseEntity
            .status(201)
            .body(BaseResponse(
                status = BaseStatus.SUCCESS,
                data = data
            ))
    }

    class BaseResponse (
        val status: BaseStatus,
        val data: Any,
    )

    class BaseErrorResponse (
        val message: String,
        val code: String
    )

    enum class BaseStatus {
        SUCCESS, ERROR
    }
}