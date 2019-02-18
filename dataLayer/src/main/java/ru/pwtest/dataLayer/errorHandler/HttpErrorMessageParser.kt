package ru.pwtest.dataLayer.errorHandler

interface HttpErrorMessageParser {
    fun isHttpException(throwable: Throwable?): Boolean
    fun parseCode(throwable: Throwable?): Int?
    fun parseMessage(throwable: Throwable?): String?
}