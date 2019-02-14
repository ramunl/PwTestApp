package ru.pwtest.delegate.error

import retrofit2.HttpException
import ru.pwtest.dataLayer.repository.ResRepo
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.CanShowMessage
import java.lang.ref.WeakReference
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val resRepo: ResRepo,
    private val httpException: HttpErrorMessageParser
) {

    data class Param(
        val errCode: Int,
        val errorMsg: String)

    fun getError(throwable: Throwable?): Param {
        var errCode = 0
        throwable?.let {
            if (it is HttpException) {
                errCode = it.code()
            }
        }
        var errorMsg = when (throwable) {
            is HttpException -> {
                val errorString = throwable.response().errorBody()?.string()
                httpException.parseCode(errorBody = errorString)
                    ?: resRepo.getString(R.string.network_error)
            }
            else -> {
                resRepo.getString(R.string.network_error)
            }
        }
        return Param(errCode, errorMsg)
    }

}