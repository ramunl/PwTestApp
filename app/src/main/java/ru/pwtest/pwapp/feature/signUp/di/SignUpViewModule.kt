package ru.pwtest.pwapp.feature.signUp.di

import dagger.Module
import dagger.Provides
import ru.pwtest.common.scope.PerActivity
import ru.pwtest.pwapp.utils.AuthValidator
import java.util.regex.Pattern

@Module
abstract class SignUpViewModule {

    @Module
    companion object {
        private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^?&+=!])(?=\\S+$).{4,}$"

        @PerActivity
        @JvmStatic
        @Provides
        fun providePasswordPattern(): Pattern = Pattern.compile(PASSWORD_PATTERN)


        @PerActivity
        @JvmStatic
        @Provides
        fun provideAuthValidator(passwordPattern: Pattern) = AuthValidator(passwordPattern)
    }

}
