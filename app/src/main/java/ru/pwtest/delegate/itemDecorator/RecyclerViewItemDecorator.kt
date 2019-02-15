package ru.pwtest.delegate.itemDecorator

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import ru.pwtest.pwapp.R
import javax.inject.Inject

class RecyclerViewItemDecorator @Inject constructor(val activity: Activity) {

    val horizontalDecoration:DividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
    init {
        val horizontalDivider = ContextCompat.getDrawable(activity, R.drawable.list_divider)
        horizontalDecoration.setDrawable(horizontalDivider!!)
    }
}