package com.example.quickstartlessons.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quickstartlessons.core.room.dao.ProductDao
import com.example.quickstartlessons.core.room.data.ProductEntity


@Database(entities = [ProductEntity::class], version = 2)
abstract class FavoriteDataBase : RoomDatabase() {
    abstract val dao: ProductDao

}