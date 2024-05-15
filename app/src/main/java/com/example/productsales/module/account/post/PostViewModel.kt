package com.example.quickstartlessons.module.account.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quickstartlessons.core.data.PostDto
import com.example.quickstartlessons.core.data.PostsDto
import com.example.quickstartlessons.core.net.ApiResultCallback
import com.example.quickstartlessons.core.net.repository.Repository
import com.example.quickstartlessons.module.base.viewmodel.BaseObservableViewModel
import kotlinx.coroutines.launch

class PostViewModel(private val repository: Repository) : BaseObservableViewModel() {

    private val _postLiveData: MutableLiveData<List<PostDto>?> = MutableLiveData()
    val postLiveData: LiveData<List<PostDto>?>
    get() = _postLiveData

    private val _postErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val postErrorLiveData: LiveData<String?>
    get() = _postErrorLiveData

    fun post(isShowLoader:Boolean=true) {
        viewModelScope.launch {
            repository.posts(object:ApiResultCallback<PostsDto?>{
                override fun onSuccess(response: PostsDto?) {
                    _postLiveData.value=response?.posts
                }

                override fun onError(): Boolean {
                    _postErrorLiveData.value="Error data"
                    return true
                }

            },isShowLoader)

        }
    }
}