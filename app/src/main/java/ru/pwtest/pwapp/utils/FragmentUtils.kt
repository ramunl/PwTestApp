package ru.pwtest.pwapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import ru.pwtest.pwapp.feature.main.view.FragmentId

fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.replaceFragment(fragmentContainer:Int, fragment: Fragment, frameId: FragmentId) {
    supportFragmentManager.inTransaction { replace(fragmentContainer, fragment, frameId.name) }
}