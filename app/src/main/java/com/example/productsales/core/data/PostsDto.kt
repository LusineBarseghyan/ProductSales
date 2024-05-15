package com.example.quickstartlessons.core.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostsDto(
    @SerializedName("posts")
    val posts: List<PostDto>
) : Serializable


data class PostDto(
    @SerializedName("id")
    val userId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
) : Serializable