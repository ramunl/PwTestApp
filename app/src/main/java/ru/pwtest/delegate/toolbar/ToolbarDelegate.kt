package ru.pwtest.delegate.toolbar

import android.app.Activity
import javax.inject.Inject

class ToolbarDelegate @Inject constructor(activity: Activity) {

    private var manager: ToolbarManager

    init {
        if (activity is ToolbarManager) this.manager = activity
        else throw ClassCastException("Your activity has to implement ToolbarTitleManager interface")
    }

    fun changeTitle(title: String) {
        manager.changeTitle(title)
    }

    fun hideToolbar() {
        manager.hideToolbar()
    }

    fun showToolbar() {
        manager.showToolbar()
    }

    fun showBackButton() {
        manager.showBackButton()
    }

    fun hideBackButton() {
        manager.hideBackButton()
    }


}