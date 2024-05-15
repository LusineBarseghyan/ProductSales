package com.example.quickstartlessons.module.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quickstartlessons.core.data.ProductDto
import com.example.quickstartlessons.core.data.ProductsDto
import com.example.quickstartlessons.core.net.ApiResultCallback
import com.example.quickstartlessons.core.net.repository.Repository
import com.example.quickstartlessons.module.base.viewmodel.BaseObservableViewModel
import kotlinx.coroutines.launch

class HomeMainTabViewModel(private val repository: Repository) : BaseObservableViewModel() {

    private val _productLiveData: MutableLiveData<List<ProductDto>?> = MutableLiveData()
    val productLiveData: LiveData<List<ProductDto>?>
        get() = _productLiveData

    private val _productErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val productErrorLiveData: LiveData<String?>
        get() = _productErrorLiveData

    fun getProduct(isShoLoader: Boolean = true) {
        viewModelScope.launch {
            repository.getProducts(object : ApiResultCallback<ProductsDto?> {
                override fun onSuccess(response: ProductsDto?) {
                    _productLiveData.value = response?.products
                }

                override fun onError(): Boolean {
                    _productErrorLiveData.value = "Error data"
                    return true
                }

            }, isShoLoader)
        }
    }

    private val _categoryLiveData: MutableLiveData<List<String>?> = MutableLiveData()
    val categoryLiveData: LiveData<List<String>?>
        get() = _categoryLiveData

    private val _categoryErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val categoryErrorLiveData: LiveData<String?>
        get() = _categoryErrorLiveData


    fun getCategories(isShoLoader: Boolean = true) {
        viewModelScope.launch {
            repository.getCategories(object : ApiResultCallback<List<String>?> {
                override fun onSuccess(response: List<String>?) {
                    _categoryLiveData.value = response
                }

                override fun onError(): Boolean {
                    _categoryErrorLiveData.value = "Error data"
                    return true
                }
            }, isShoLoader)
        }
    }

    fun geProductByCategory(isShoLoader: Boolean = true, id: String) {
        viewModelScope.launch {
            repository.getProductByCategory(object : ApiResultCallback<ProductsDto?> {
                override fun onSuccess(response: ProductsDto?) {
                    _productLiveData.value = response?.products
                }

                override fun onError(): Boolean {
                    _productErrorLiveData.value = "Error data"
                    return true
                }

            }, isShoLoader, id)
        }
    }



}
