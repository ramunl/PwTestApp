package ru.pwtest.pwapp.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : MvpAppCompatFragment() {

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        compositeDisposable = CompositeDisposable()
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.clear()
    }

    fun addDisposable(disposable:Disposable?) {
        disposable?.let { compositeDisposable.add(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes(), container, false)

    @LayoutRes
    protected abstract fun layoutRes(): Int

}
