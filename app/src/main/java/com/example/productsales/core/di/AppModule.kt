package com.example.quickstartlessons.core.di

import com.example.quickstartlessons.module.account.post.PostFragment
import com.example.quickstartlessons.module.account.post.PostViewModel
import com.example.quickstartlessons.module.home.ui.HomeMainTabFragment
import com.example.quickstartlessons.module.home.ui.HomeMainTabViewModel
import com.example.quickstartlessons.module.home.ui.details.ProductDetailsFragment
import com.example.quickstartlessons.module.home.ui.details.ProductDetailsViewModel
import com.example.quickstartlessons.module.home.ui.serch.SearchFragment
import com.example.quickstartlessons.module.home.ui.serch.SearchViewModel
import com.example.quickstartlessons.module.launch.SplashActivity
import com.example.quickstartlessons.module.launch.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val appModule = module {

    scope(named<HomeMainTabFragment>()) {
        viewModel { HomeMainTabViewModel(get()) }
    }
    scope(named<ProductDetailsFragment>()) {
        viewModel { ProductDetailsViewModel(get()) }
    }
    scope(named<SplashActivity>()) {
        viewModel { SplashViewModel(get()) }
    }
    scope(named<SearchFragment>()) {
        viewModel { SearchViewModel(get()) }
    }

    scope(named<PostFragment>()) {
        viewModel { PostViewModel(get()) }
    }
}
