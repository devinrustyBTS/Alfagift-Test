package id.bts.devin.alfagift_test.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel: ViewModel() {
    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        detach()
    }

    protected fun collect(s: Disposable) {
        disposable.add(s)
    }

    private fun detach() {
        disposable.dispose()
        disposable.clear()
    }
}