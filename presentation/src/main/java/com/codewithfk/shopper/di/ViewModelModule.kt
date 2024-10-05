package com.codewithfk.shopper.di

import com.codewithfk.shopper.ui.feature.cart.CartViewModel
import com.codewithfk.shopper.ui.feature.home.HomeViewModel
import com.codewithfk.shopper.ui.feature.product_details.ProductDetailsViewModel
import com.codewithfk.shopper.ui.feature.summary.CartSummaryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        ProductDetailsViewModel(get())
    }
    viewModel {
        CartViewModel(get(),get(),get())
    }
    viewModel {
        CartSummaryViewModel(get())
    }
}