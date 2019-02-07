package ru.pwtest.delegate.date

import java.util.*
import javax.inject.Inject

class DateDelegate @Inject constructor() {

    private var cacheTime: Date? = null

    fun getCurrentDate(): Date? {
        if (cacheTime == null) {
            cacheTime = Calendar.getInstance().time
        }
        return cacheTime
    }

    fun isNotObsoleteDate(current: Date?): Boolean {
        current?.let {
            return (Calendar.getInstance().timeInMillis - it.time) / (24 * 60 * 60 * 1000) < 1
        }
        return false
    }


}