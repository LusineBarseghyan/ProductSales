package com.example.quickstartlessons.module.base.coroutine

import android.util.Log
import com.example.quickstartlessons.module.base.utils.QsConstants.EMPTY_STRING
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class BaseCoroutineExceptionHandler(override val key: CoroutineContext.Key<*>) : CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        try {
            Log.e("Global error", exception.localizedMessage ?: EMPTY_STRING)
            // remove loader
        } catch (ex: Exception) {
            Log.e("Coroutine Exception", ex.localizedMessage ?: EMPTY_STRING)
        }
    }
}