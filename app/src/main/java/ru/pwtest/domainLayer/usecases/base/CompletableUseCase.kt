package ru.pwtest.domainLayer.usecases.base

import io.reactivex.Completable

abstract class CompletableUseCase<in PARAMS> {

    fun execute(parameters: PARAMS) = build(parameters)

    internal abstract fun build(parameters: PARAMS): Completable
}