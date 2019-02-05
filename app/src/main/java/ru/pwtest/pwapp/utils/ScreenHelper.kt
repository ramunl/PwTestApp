package ru.pwtest.pwapp.utils

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(context: AppCompatActivity) {
    try {
        val view = context.currentFocus
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}