package ru.pwtest.pwapp.base

import kotlinx.android.synthetic.main.app_bar_main.*
import ru.pwtest.delegate.toolbar.ToolbarManager

abstract class BaseToolbarActivity : BaseActivity(), ToolbarManager {

    protected fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(false)
            setDisplayShowTitleEnabled(true)
        }
    }

    override fun showBackButton() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun hideBackButton() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun changeTitle(title: String) {
        supportActionBar?.let { setTitle(title) }
    }

    override fun hideToolbar() {
        supportActionBar?.hide()
    }

    override fun showToolbar() {
        supportActionBar?.show()
    }
}