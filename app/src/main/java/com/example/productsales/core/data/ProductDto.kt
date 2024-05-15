package com.example.quickstartlessons.core.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductsDto(
    @SerializedName("products")
    val products: List<ProductDto>
) : Serializable

data class ProductDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("discountPercentage")
    val discountPercentage: Double,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("images")
    val image: List<String>,
    var favorite: Boolean = false
) : Serializable

