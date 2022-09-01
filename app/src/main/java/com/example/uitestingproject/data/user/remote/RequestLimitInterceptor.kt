package com.example.uitestingproject.data.user.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.atomic.AtomicInteger

class RequestLimitInterceptor: Interceptor {

    private val atomicInteger = AtomicInteger(27)

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentValue = atomicInteger.decrementAndGet()
        if (currentValue <= 0) {
            updateAtomicInteger()
            atomicInteger.decrementAndGet()
        }
        return chain.proceed(chain.request())
    }

    @Synchronized fun updateAtomicInteger() {
        if (atomicInteger.get() <= 0) {
            Thread.sleep(2000L)
            atomicInteger.set(30)
        }
    }
}