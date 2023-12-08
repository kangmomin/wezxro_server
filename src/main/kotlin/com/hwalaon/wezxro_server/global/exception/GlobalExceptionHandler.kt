package com.hwalaon.wezxro_server.global.exception

import com.hwalaon.wezxro_server.global.BasicResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BasicException::class)
    fun basicExceptionHandler(e: BasicException) = BasicResponse.error(e.errorCode)

    @ExceptionHandler(Exception::class)
    fun unexpectedExceptionHandler(e: Exception) {
        e.stackTrace
        BasicResponse.error(ErrorCode.UNEXPECTED_ERROR)
    }
}