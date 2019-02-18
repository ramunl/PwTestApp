package ru.pwtest.domain.usecases.base

import io.reactivex.Maybe

abstract class MaybeUseCase<in PARAMS, RESULT> {

    fun execute(parameters: PARAMS) = build(parameters)

    internal abstract fun build(parameters: PARAMS): Maybe<RESULT>
}

