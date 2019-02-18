package ru.pwtest.pwapp.delegate.toolbar

interface ToolbarManager {

    fun changeTitle(title: String)
    fun hideToolbar()
    fun showToolbar()
    fun showBackButton()
    fun hideBackButton()
}