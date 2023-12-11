package com.hwalaon.wezxro_server.global.exception

import com.hwalaon.wezxro_server.global.BasicResponse
import com.hwalaon.wezxro_server.global.exception.dto.MsgResponse
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BasicException::class)
    fun basicExceptionHandler(e: BasicException) = BasicResponse.error(e.errorCode)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun jsonExceptionHandler(e: HttpMessageNotReadableException) =
        BasicResponse.error(ErrorCode.NON_BODY_ERROR)

    @ExceptionHandler(Exception::class)
    fun unexpectedExceptionHandler(e: Exception): ResponseEntity<MsgResponse> {
        e.printStackTrace()
        return BasicResponse.error(ErrorCode.UNEXPECTED_ERROR)
    }
}