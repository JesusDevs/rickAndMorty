package com.rickandmorty.remote

import com.example.rickandmorty.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object Retrofit {
    //end point url del API

    const val BASE_URL = "https://rickandmortyapi.com/api/"
    //constantes para read/write timeout de la conexion
    private const val NETWORK_CALL_TIME_OUT = 60L
    private lateinit var okHttpClient: OkHttpClient

        fun getRetrofitInstance(): ICharacterService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            okHttpClient = OkHttpClient.Builder()
                .readTimeout(NETWORK_CALL_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_CALL_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    }
                    else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }).build()


            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ICharacterService::class.java)
        }
 }