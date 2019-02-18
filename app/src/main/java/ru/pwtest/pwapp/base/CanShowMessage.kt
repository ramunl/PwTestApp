package ru.pwtest.pwapp.base

import ru.pwtest.domain.error.ErrorHandler

interface CanShowMessage {

    fun showErrorMessage(errorParam: ErrorHandler.Param)
    fun showSuccessMessage(text: String)



}