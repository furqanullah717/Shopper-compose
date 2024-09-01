package com.codewithfk.data.di

import com.codewithfk.data.repository.CategoryRepositoryImpl
import com.codewithfk.data.repository.ProductRepositoryImpl
import com.codewithfk.domain.repository.CategoryRepository
import com.codewithfk.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
}