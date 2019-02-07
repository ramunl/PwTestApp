package ru.pwtest.pwapp.base

interface CanShowMessage {
    fun showErrorMessage(text: String)
    fun showSuccessMessage(text: String)
}