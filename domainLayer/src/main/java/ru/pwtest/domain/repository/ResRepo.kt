package ru.pwtest.domain.repository

import android.content.Context
import android.support.annotation.StringRes
import javax.inject.Inject

class ResRepo @Inject constructor(
        private val context: Context
) {
    fun getString(@StringRes id: Int): String = context.resources.getString(id)
}