package com.example.quickstartlessons.core.di

import com.example.quickstartlessons.core.net.ProductDataSource
import com.example.quickstartlessons.core.net.createOkHttpClient
import com.example.quickstartlessons.core.net.createWebService
import com.example.quickstartlessons.core.net.repository.Repository
import com.example.quickstartlessons.core.net.repository.RepositoryImplementation
import com.example.quickstartlessons.module.base.utils.QsConstants.PRODUCTS_BASE_URL
import org.koin.dsl.module

internal val apiModule = module {

    single { createWebService<ProductDataSource>(createOkHttpClient(), PRODUCTS_BASE_URL) }

    single<Repository> { RepositoryImplementation(get()) }

}