package ru.pwtest.pwapp.feature.main.interactior

import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class LoggedUserInteractor @Inject constructor() {

    var interactor: BehaviorSubject<Void> = BehaviorSubject.create<Void>()

}