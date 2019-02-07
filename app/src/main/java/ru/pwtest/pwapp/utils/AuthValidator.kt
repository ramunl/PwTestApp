package andrey.murzin.travelmate.utils

import android.text.TextUtils
import java.util.regex.Pattern
import javax.inject.Inject


class AuthValidator @Inject constructor(
        private val passwordPattern: Pattern) {


    fun isValidPassword(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && passwordPattern.matcher(target).matches()
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}