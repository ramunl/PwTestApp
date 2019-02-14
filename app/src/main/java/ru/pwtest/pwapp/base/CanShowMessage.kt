package ru.pwtest.pwapp.base

import ru.pwtest.delegate.error.ErrorHandler

interface CanShowMessage {

    fun showErrorMessage(errorParam: ErrorHandler.Param)
    fun showSuccessMessage(text: String)



}