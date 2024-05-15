package com.example.quickstartlessons.core.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quickstartlessons.core.room.data.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity)

    @Query("SELECT* FROM product_table ")
    fun getAllProduct(): LiveData<List<ProductEntity>>

    @Query("DELETE FROM product_table WHERE id = :id")
    suspend fun deleteProductById(id: Int)
}