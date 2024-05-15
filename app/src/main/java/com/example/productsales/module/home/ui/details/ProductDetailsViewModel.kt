package com.example.quickstartlessons.module.home.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quickstartlessons.core.data.ProductDto
import com.example.quickstartlessons.core.net.ApiResultCallback
import com.example.quickstartlessons.core.net.repository.Repository
import com.example.quickstartlessons.module.base.viewmodel.BaseObservableViewModel
import kotlinx.coroutines.launch

class ProductDetailsViewModel(private val repository: Repository) : BaseObservableViewModel() {
    private val _productByIdLiveData: MutableLiveData<ProductDto?> = MutableLiveData()
    val productByIdLiveData: LiveData<ProductDto?>
        get() = _productByIdLiveData

    private val _productByIdErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val productByIdErrorLiveData: LiveData<String?>
        get() = _productByIdErrorLiveData

    fun getProductById(isShoLoader: Boolean = true, id: Int) {
        viewModelScope.launch {
            repository.getProductById(object : ApiResultCallback<ProductDto?> {
                override fun onSuccess(response: ProductDto?) {
                    _productByIdLiveData.value = response
                }

                override fun onError(): Boolean {
                    _productByIdErrorLiveData.value = "Error data"
                    return true
                }

            }, isShoLoader, id)
        }
    }
}