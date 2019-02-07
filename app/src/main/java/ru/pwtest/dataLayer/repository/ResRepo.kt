package ru.pwtest.dataLayer.repository

import android.content.Context
import android.support.annotation.StringRes
import javax.inject.Inject

class ResRepo @Inject constructor(
        private val context: Context
) {

    fun getString(@StringRes id: Int) = context.resources.getString(id)

}