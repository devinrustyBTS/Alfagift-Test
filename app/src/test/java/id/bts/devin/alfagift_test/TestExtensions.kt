package id.bts.devin.alfagift_test

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import id.bts.devin.alfagift_test.presentation.utils.LiveResource
import id.bts.devin.alfagift_test.presentation.utils.Resource
import io.reactivex.rxjava3.observers.TestObserver
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveResource<T>.getOrAwaitValueTest(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: (Resource<T>) -> Unit = {}
): Resource<T> {
    var data: Resource<T>? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<Resource<T>> {
        override fun onChanged(o: Resource<T>?) {
            println(o)
            data = o
            latch.countDown()
            this@getOrAwaitValueTest.removeObserver(this)
        }
    }

    this.observeForever(observer)

    try {
        data?.let { afterObserve.invoke(it) }

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as Resource<T>
}