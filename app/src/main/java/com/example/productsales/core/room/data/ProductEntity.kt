package com.example.quickstartlessons.core.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product_table")
class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val brand: String,
    val thumbnail: String,

)