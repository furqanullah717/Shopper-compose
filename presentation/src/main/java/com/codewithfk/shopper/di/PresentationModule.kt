package com.codewithfk.shopper.di

import com.codewithfk.shopper.ShopperSession
import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModule)
    single { ShopperSession(get()) }
}