package id.bts.devin.alfagift_test.presentation.utils

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class LiveResource<T> {

    private val _data = MutableLiveData<Resource<T>>()

    private val data: LiveData<Resource<T>> = _data

    fun postValue(value: Resource<T>) {
        _data.postValue(value)
    }

    fun observe(
        @NonNull owner: LifecycleOwner,
        @Nullable onLoading: ((data: T?) -> Unit)? = null,
        @Nullable onSuccess: ((data: T?) -> Unit)? = null,
        @Nullable onError: ((throwable: Throwable) -> Unit)? = null
    ) {
        data.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    onLoading?.invoke(it.data)
                }
                Resource.Status.SUCCESS -> {
                    onSuccess?.invoke(it.data)
                }
                Resource.Status.ERROR -> {
                    onError?.invoke(it.throwable!!)
                }
            }
        }
    }

    fun removeObserver(observer: Observer<Resource<T>>) {
        data.removeObserver(observer)
    }

    fun observeForever(observer: Observer<Resource<T>>) {
        data.observeForever(observer)
    }
}

class Resource<T> private constructor(
    @NonNull val status: Status,
    @Nullable val data: T?,
    @Nullable val throwable: Throwable?
) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(@NonNull data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable, @Nullable data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, throwable)
        }

        fun <T> loading(@Nullable data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}