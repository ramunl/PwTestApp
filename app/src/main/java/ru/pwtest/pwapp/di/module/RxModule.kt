package ru.pwtest.pwapp.di.module
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.pwtest.pwapp.di.ComputationScheduler
import ru.pwtest.pwapp.di.IoScheduler
import ru.pwtest.pwapp.di.MainScheduler

@Module
class RxModule {

    @Provides
    @IoScheduler
    fun provideSchedulerIO(): Scheduler = Schedulers.io()

    @Provides
    @ComputationScheduler
    fun provideSchedulerComputation(): Scheduler = Schedulers.computation()

    @Provides
    @MainScheduler
    fun provideSchedulerMainThread(): Scheduler = AndroidSchedulers.mainThread()

}
