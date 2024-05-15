package com.example.quickstartlessons.module.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quickstartlessons.core.data.UsersDto
import com.example.quickstartlessons.core.net.ApiResultCallback
import com.example.quickstartlessons.core.net.repository.Repository
import com.example.quickstartlessons.module.base.viewmodel.BaseObservableViewModel
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: Repository) : BaseObservableViewModel() {

    private val _usersLiveData: MutableLiveData<UsersDto?> = MutableLiveData()
    val usersLiveData: LiveData<UsersDto?>
        get() = _usersLiveData
    private val _usersErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val usersErrorLiveData: LiveData<String?>
        get() = _usersErrorLiveData


    fun getUsers(isShowLoader: Boolean = true) {
        viewModelScope.launch {
            repository.getUsers(object : ApiResultCallback<UsersDto?> {
                override fun onSuccess(response: UsersDto?) {
                    _usersLiveData.value = response
                }

                override fun onError(): Boolean {
                    _usersErrorLiveData.value = "Error data"
                    return true
                }

            }, isShowLoader)
        }
    }
}