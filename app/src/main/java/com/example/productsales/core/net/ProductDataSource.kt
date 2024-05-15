package com.example.quickstartlessons.core.net

import com.example.quickstartlessons.core.data.PostsDto
import com.example.quickstartlessons.core.data.ProductDto
import com.example.quickstartlessons.core.data.ProductsDto
import com.example.quickstartlessons.core.data.UsersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductDataSource {
    @GET("products")
    suspend fun getProduct(): Response<ProductsDto>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ProductDto>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    @GET("products/category/{categoryName}")
    suspend fun getProductByCategory(@Path("categoryName") id: String): Response<ProductsDto>

    @GET("users")
    suspend fun getAllUsers(): Response<UsersDto>

    @GET("products/search")
    suspend fun getSearchProduct(@Query("q")name :String): Response<ProductsDto>

    @GET("posts")
    suspend fun getUserPosts(): Response<PostsDto>

}