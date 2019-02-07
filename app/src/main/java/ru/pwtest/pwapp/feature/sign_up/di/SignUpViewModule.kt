package andrey.murzin.travelmate.presentation.feature.registration.di

import andrey.murzin.travelmate.utils.AuthValidator

import dagger.Module
import dagger.Provides
import ru.pwtest.pwapp.di.PerActivity
import java.util.regex.Pattern

@Module
abstract class SignUpViewModule {


    @Module
    companion object {
        private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^?&+=!])(?=\\S+$).{4,}$"

        @PerActivity
        @JvmStatic
        @Provides
        fun providePasswordPattern() = Pattern.compile(PASSWORD_PATTERN)


        @PerActivity
        @JvmStatic
        @Provides
        fun provideAuthValidator(passwordPattern: Pattern) = AuthValidator(passwordPattern)
    }

}
