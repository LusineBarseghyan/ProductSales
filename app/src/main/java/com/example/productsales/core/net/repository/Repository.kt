package com.example.quickstartlessons.core.net.repository

import com.example.quickstartlessons.core.data.PostsDto
import com.example.quickstartlessons.core.data.ProductDto
import com.example.quickstartlessons.core.data.ProductsDto
import com.example.quickstartlessons.core.net.ApiResultCallback
import com.example.quickstartlessons.core.net.ProductDataSource
import com.example.quickstartlessons.core.net.getHttpResponse
import com.example.quickstartlessons.core.data.UsersDto


interface Repository {
    suspend fun getProducts(resultCallback: ApiResultCallback<ProductsDto?>, isShowLoader: Boolean)
    suspend fun getCategories(resultCallback: ApiResultCallback<List<String>?>, isShowLoader: Boolean)
    suspend fun getProductByCategory(resultCallback: ApiResultCallback<ProductsDto?>, isShowLoader: Boolean, id:String)
    suspend fun getProductById(resultCallback: ApiResultCallback<ProductDto?>, isShowLoader: Boolean, id:Int)
    suspend fun getUsers(resultCallback: ApiResultCallback<UsersDto?>, isShowLoader: Boolean)
    suspend fun search(resultCallback: ApiResultCallback<ProductsDto?>, isShowLoader: Boolean,name:String)
    suspend fun posts(resultCallback: ApiResultCallback<PostsDto?>, isShowLoader: Boolean)
}

class RepositoryImplementation(private val dataSource: ProductDataSource) : Repository {

    override suspend fun getProducts(resultCallback: ApiResultCallback<ProductsDto?>, isShowLoader: Boolean) {
        getHttpResponse(resultCallback, isShowLoader) {
            dataSource.getProduct()
        }
    }

    override suspend fun getCategories(resultCallback: ApiResultCallback<List<String>?>, isShowLoader: Boolean) {
        getHttpResponse(resultCallback,isShowLoader) {
            dataSource.getCategories()
        }
    }
    override suspend fun getProductByCategory(resultCallback: ApiResultCallback<ProductsDto?>, isShowLoader: Boolean, id: String) {
        getHttpResponse(resultCallback,isShowLoader){
         dataSource.getProductByCategory(id)
        }
    }

    override suspend fun getProductById(resultCallback: ApiResultCallback<ProductDto?>, isShowLoader: Boolean, id: Int) {
        getHttpResponse(resultCallback,isShowLoader){
            dataSource.getProductById(id)
        }
    }

    override suspend fun getUsers(resultCallback: ApiResultCallback<UsersDto?>, isShowLoader: Boolean) {
        getHttpResponse(resultCallback, isShowLoader) {
            dataSource.getAllUsers()
        }
    }

    override suspend fun search(resultCallback: ApiResultCallback<ProductsDto?>, isShowLoader: Boolean, name: String) {
       getHttpResponse(resultCallback, isShowLoader){
           dataSource.getSearchProduct(name)
       }
    }

    override suspend fun posts(resultCallback: ApiResultCallback<PostsDto?>, isShowLoader: Boolean) {
        getHttpResponse(resultCallback, isShowLoader) {
            dataSource.getUserPosts()
        }
    }
}
