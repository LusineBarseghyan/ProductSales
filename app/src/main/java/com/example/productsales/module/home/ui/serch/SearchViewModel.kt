package com.example.quickstartlessons.module.home.ui.serch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quickstartlessons.core.data.ProductDto
import com.example.quickstartlessons.core.data.ProductsDto
import com.example.quickstartlessons.core.net.ApiResultCallback
import com.example.quickstartlessons.core.net.repository.Repository
import com.example.quickstartlessons.module.base.viewmodel.BaseObservableViewModel
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : BaseObservableViewModel() {
    private val _searchProductLiveData: MutableLiveData<List<ProductDto>?> = MutableLiveData()
    val searchProductLiveData: LiveData<List<ProductDto>?>
        get() = _searchProductLiveData

    private val _searchProductErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val searchProductErrorLiveData: LiveData<String?>
        get() = _searchProductErrorLiveData

    fun search(isShowLoader: Boolean = true, name: String) {
        viewModelScope.launch {
            repository.search(object : ApiResultCallback<ProductsDto?> {
                override fun onSuccess(response: ProductsDto?) {
                    _searchProductLiveData.value = response?.products
                }

                override fun onError(): Boolean {
                    _searchProductErrorLiveData.value = "No result"
                    return true
                }

            }, isShowLoader, name)
        }
    }

    fun clearValue() {
        _searchProductLiveData.value = emptyList()
    }
}