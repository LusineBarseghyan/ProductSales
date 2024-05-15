package com.example.quickstartlessons.core.di


import androidx.room.Room
import com.example.quickstartlessons.core.room.FavoriteManager
import com.example.quickstartlessons.core.room.database.FavoriteDataBase
import org.koin.dsl.module


internal val roomModule = module {
    single {
        Room.databaseBuilder(get(), FavoriteDataBase::class.java, "product_database").fallbackToDestructiveMigration().build()
    }
    single {
        get<FavoriteDataBase>().dao
    }
    single {
        FavoriteManager(get())
    }
}
