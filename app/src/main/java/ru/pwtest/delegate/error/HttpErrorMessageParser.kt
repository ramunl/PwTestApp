package ru.pwtest.delegate.error

import com.google.gson.Gson
import com.google.gson.JsonObject
import ru.pwtest.pwapp.di.PerApplication
import javax.inject.Inject


@PerApplication
class HttpErrorMessageParser @Inject constructor(private val gson: Gson) {

    fun parseCode(errorBody: String?): String? {
        return try {
            gson.fromJson(errorBody, JsonObject::class.java)
                    .get("non_field_errors")
                    .asString
        } catch (e: Exception) {
            errorBody?.let {
                if (it.isNotEmpty()) errorBody
                else null
            }

        }
    }
}
