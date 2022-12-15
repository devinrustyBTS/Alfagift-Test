package id.bts.devin.alfagift_test.presentation.utils

import id.bts.devin.alfagift_test.data.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class StateResource<T> {
    private val _data: MutableStateFlow<NetworkResult<T?>> =
        MutableStateFlow(NetworkResult.Success(null))

    val data = _data.asStateFlow()

    fun postValue(value: NetworkResult<T>) {
        _data.value = value
    }

    suspend fun collect(
        onLoading: (() -> Unit)? = null,
        onSuccess: ((data: T) -> Unit),
        onError: ((throwable: Throwable) -> Unit)? = null
    ) {
        data.collectLatest { state ->
            when {
                state.isLoading -> onLoading?.invoke()
                state.isSucceeded -> state.success { data ->
                    data?.let { onSuccess.invoke(it) }
                }
                state.isError -> state.error { onError?.invoke(it) }
            }
        }
    }
}

class StateBasic<T> {
    private val _data: MutableStateFlow<T?> = MutableStateFlow(null)

    val data = _data.asStateFlow()

    fun postValue(value: T) {
        _data.value = value
    }

    suspend fun collect(
        onError: ((throwable: Throwable) -> Unit)? = null,
        onSuccess: ((data: T) -> Unit),
    ) {
        data.collectLatest { state ->
            when (state) {
                null -> onError?.invoke(Throwable())
                else -> onSuccess.invoke(state)
            }
        }
    }
}