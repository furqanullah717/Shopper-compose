package com.codewithfk.shopper

import android.app.Application
import com.codewithfk.data.di.dataModule
import com.codewithfk.domain.di.domainModule
import com.codewithfk.shopper.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopperApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShopperApp)
            modules(listOf(
                presentationModule,
                dataModule,
                domainModule
            ))
        }
    }
}