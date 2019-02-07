package ru.pwtest.domainLayer.usecases.base

import io.reactivex.Single

abstract class SingleUseCase<in PARAMS, RESULT> {

    fun execute(parameters: PARAMS) = build(parameters)

    internal abstract fun build(parameters: PARAMS): Single<RESULT>
}

