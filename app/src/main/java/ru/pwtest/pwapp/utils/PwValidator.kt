package ru.pwtest.pwapp.utils


fun isPwFormatValid(target: String): Boolean {
    return try {
        target.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}


