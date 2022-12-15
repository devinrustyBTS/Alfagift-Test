package id.bts.devin.alfagift_test.data.utils

sealed class NetworkResult<out R> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val throwable: Throwable) : NetworkResult<Nothing>()
    object Loading: NetworkResult<Nothing>()
}

val NetworkResult<*>?.isSucceeded get() = this != null && this is NetworkResult.Success && data != null

val NetworkResult<*>?.isError get() = this != null && this is NetworkResult.Error

val NetworkResult<*>?.isLoading get() = this != null && this is NetworkResult.Loading

inline infix fun <T> NetworkResult<T>.success(predicate: (data: T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success && this.data != null) {
        predicate.invoke(this.data)
    }
    return this
}

inline infix fun <T> NetworkResult<T>.error(predicate: (data: Throwable) -> Unit) {
    if (this is NetworkResult.Error) {
        predicate.invoke(this.throwable)
    }
}