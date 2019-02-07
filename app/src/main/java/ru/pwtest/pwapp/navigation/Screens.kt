package ru.pwtest.pwapp.navigation

sealed class Screen {
    fun getKey(): String = this::class.java.simpleName

    object Auth : Screen()
    object Main : Screen()
    object Registration : Screen()

}