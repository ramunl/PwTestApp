package ru.pwtest.pwapp.base

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface CanShowMessage {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorMessage(text: String, errCode: Int? = null)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSuccessMessage(text: String)
}