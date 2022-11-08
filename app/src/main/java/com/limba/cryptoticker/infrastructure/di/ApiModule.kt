package com.limba.cryptoticker.infrastructure.di

import com.limba.cryptoticker.infrastructure.api.BitfinexApiContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api-pub.bitfinex.com/v2/")
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideBitfinexApiContract(retrofit: Retrofit): BitfinexApiContract =
        retrofit.create(BitfinexApiContract::class.java)
}