package ru.pwtest.pwapp.base

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_toolbar.*
import ru.pwtest.pwapp.feature.main.view.MainActivity
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    protected lateinit var disposable: CompositeDisposable


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        disposable = CompositeDisposable()
        setContentView(layoutRes())
        setSupportActionBar(toolbar)
        viewCreated(savedInstanceState != null)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    fun runMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected abstract fun viewCreated(isRestoring:Boolean)
}
