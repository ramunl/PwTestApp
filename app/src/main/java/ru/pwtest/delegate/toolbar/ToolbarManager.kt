package ru.pwtest.delegate.toolbar

interface ToolbarManager {

    fun changeTitle(title: String)
    fun hideToolbar()
    fun showToolbar()
    fun showBackButton()
    fun hideBackButton()
}