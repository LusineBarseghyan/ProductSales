package com.example.productsales

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.quickstartlessons.core.data.UserDto
import com.example.quickstartlessons.core.di.appComponent
import com.example.quickstartlessons.module.base.coroutine.BaseCoroutineExceptionHandler
import com.example.quickstartlessons.module.base.utils.PreferencesManager
import com.example.quickstartlessons.module.base.utils.Prefs
import com.example.quickstartlessons.core.data.UsersDto
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.Locale

class QSApplication : Application(), LifecycleObserver {
    private lateinit var currentActivityState: Lifecycle

    override fun onCreate() {
        super.onCreate()
        instance = this
        setupKoin()
        currentActivityState = ProcessLifecycleOwner.get().lifecycle
        currentActivityState.addObserver(this)
        listenToNetworkChange()
        Prefs.Builder()
            .setContext(this)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        Lingver.init(this, Locale(PreferencesManager.getCurrentLanguage()))
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@QSApplication)
            androidFileProperties()
            modules(appComponent)
        }
    }

    private fun listenToNetworkChange() {
        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isLastNetworkStateWasConnected = true
                networkStateLiveData.postValue(true)
            }

            override fun onLost(network: Network) {
                if (isLastNetworkStateWasConnected == true && (currentActivityState.currentState == Lifecycle.State.STARTED || currentActivityState.currentState == Lifecycle.State.RESUMED)) {
                    if (System.currentTimeMillis() - (lastNoInternetShownToastTime ?: 0) > SHOW_NO_INTERNET_CONNECTION_POPUP_TIME_RANGE) {
                        lastNoInternetShownToastTime = System.currentTimeMillis()
                    }
                }
                isLastNetworkStateWasConnected = false
                networkStateLiveData.postValue(false)
            }
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }

    companion object {
        lateinit var instance: Application
        val networkStateLiveData: MutableLiveData<Boolean> = MutableLiveData()
        var isLastNetworkStateWasConnected: Boolean? = null
        var lastNoInternetShownToastTime: Long? = null
        const val SHOW_NO_INTERNET_CONNECTION_POPUP_TIME_RANGE = 4000
        val userProfileLiveData: MutableLiveData<UserDto?> = MutableLiveData()
        fun getCoroutineContext() = Dispatchers.Main + SupervisorJob() + BaseCoroutineExceptionHandler(
            CoroutineExceptionHandler
        )
    }
}