package ru.pwtest.domain.usecases.base

import io.reactivex.Observable

abstract class ObservableUseCase<in PARAMS, RESULT> {

    fun execute(parameters: PARAMS) = build(parameters)

    internal abstract fun build(parameters: PARAMS): Observable<RESULT>
}