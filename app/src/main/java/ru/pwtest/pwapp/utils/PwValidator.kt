package ru.pwtest.pwapp.utils

import timber.log.Timber

class PwValidator(wpAmount: String) {

    var isValid: Boolean = false
    var wpAmountVal: Int = 0

    init {
        isValid = try {
            wpAmountVal = Integer.parseInt(wpAmount)
            true
        } catch (e: Exception) {
            Timber.e(e)
            false
        }
    }
}


