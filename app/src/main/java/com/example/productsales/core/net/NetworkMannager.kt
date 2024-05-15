package com.example.quickstartlessons.core.net

import androidx.annotation.Keep
import com.example.quickstartlessons.module.base.fragment.BaseFragment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(60L, TimeUnit.SECONDS)
    .readTimeout(60L, TimeUnit.SECONDS)
    .addInterceptor(
        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    )
    .build()

@Suppress("unused")
inline fun <reified T> createWebService(okHttpClient: OkHttpClient, baseUrl: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonCreatorHelper.gsonForApiRequest)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}

object GsonCreatorHelper {
    val gsonForApiRequest: GsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder()
            .setLenient()
            .enableComplexMapKeySerialization()
            .create()
    )
}

suspend fun <T> getHttpResponse(
    resultCallBack: ApiResultCallback<T?>,
    isShowLoader: Boolean = true,
    apiFunction: suspend () -> Response<T>
) {
    if (isShowLoader) {
        BaseFragment.addLoader()
    }
    withContext(
        Dispatchers.IO + MyCoroutineExceptionHandler(
            CoroutineExceptionHandler,
            resultCallBack
        )
    ) {
        val response = apiFunction()
        val responseBody = response.body()

        withContext(
            Dispatchers.Main + MyCoroutineExceptionHandler(
                CoroutineExceptionHandler, resultCallBack
            )
        ) {
            if (isShowLoader) {
                BaseFragment.removeLoader()
            }
            if (response.isSuccessful) {
                resultCallBack.onSuccess(responseBody)
            } else {
                //Remove loader again for sequence calls
                if (isShowLoader) {
                    BaseFragment.removeLoader()
                }
                resultCallBack.onNotHandledError(response.errorBody())
            }
        }
    }
}

@Keep
interface ApiResultCallback<T> {
    fun onSuccess(response: T)

    fun onError(): Boolean = false

    fun onNotHandledError(error: Any? = null) {}
}