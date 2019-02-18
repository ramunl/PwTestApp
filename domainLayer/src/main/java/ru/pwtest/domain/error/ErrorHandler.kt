package ru.pwtest.domain.error

import com.example.domainlayer.R
import ru.pwtest.dataLayer.errorHandler.HttpErrorMessageParser
import ru.pwtest.domain.repository.ResRepo
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val resRepo: ResRepo,
    private val httpException: HttpErrorMessageParser
) {

    data class Param(
        val errCode: Int?,
        val errorMsg: String = ""
    )

    fun getError(throwable: Throwable?): Param {
        var errCode: Int? = 0
        var errorMsg: String

        val errDefault = resRepo.getString(R.string.network_error)

        if (httpException.isHttpException(throwable)) {
            errCode = httpException.parseCode(throwable)
            errorMsg = httpException.parseMessage(throwable) ?: errDefault
        } else {
            errorMsg = errDefault
        }

        return Param(errCode, errorMsg)
    }

}