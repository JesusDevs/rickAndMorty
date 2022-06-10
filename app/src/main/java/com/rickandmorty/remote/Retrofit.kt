package com.rickandmorty.remote

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

 object Retrofit {
    const val BASE_URL = "https://rickandmortyapi.com/api/"
        fun getRetrofitInstance(): ICharacterService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ICharacterService::class.java)
        }
 }