package com.rickandmorty.remote

import com.rickandmorty.model.pojo.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ICharacterService {

    /**@author : JysusDev
     * @sample
     * Interfaz para obtener los personajes de Rick and Morty desde object retrofit
     */

    @GET("character")
    suspend fun getAllCharacter(@Query("page") page: Int) : Response<CharacterResponse>
    @GET("character")
    suspend fun searchAllCharacter(@Query("name") name: String ,
                                   @Query("page") page: Int) : Response<CharacterResponse>

}