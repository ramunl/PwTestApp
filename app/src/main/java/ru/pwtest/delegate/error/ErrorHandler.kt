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

    private var errorView: WeakReference<CanShowMessage>? = null

    fun attachView(view: CanShowMessage) {
        errorView = WeakReference(view)
    }

    fun onDetach() {
        errorView = null
    }

    fun handleError(throwable: Throwable?) {
        throwable?.let { errorView?.get()?.showErrorMessage(getErrorMessage(throwable)) }
    }

    private fun getErrorMessage(throwable: Throwable) =
            when (throwable) {
                is HttpException -> {
                    val errorString = throwable.response().errorBody()?.string()
                    httpException.parseCode(errorBody = errorString)
                            ?: resRepo.getString(R.string.network_error)
                }
                else -> {
                    resRepo.getString(R.string.network_error)
                }
            }
}