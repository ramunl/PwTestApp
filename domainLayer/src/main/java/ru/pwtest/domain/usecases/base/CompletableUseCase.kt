package ru.pwtest.domain.usecases.base

import io.reactivex.Completable

abstract class CompletableUseCase<in PARAMS> {

    fun execute(parameters: PARAMS) = build(parameters)
    abstract fun build(parameters: PARAMS): Completable
}