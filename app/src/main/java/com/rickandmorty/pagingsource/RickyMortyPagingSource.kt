package com.rickandmorty.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmorty.model.pojo.ResultCharacter
import com.rickandmorty.remote.ICharacterService

class RickyMortyPagingSource(private val apiService: ICharacterService) : PagingSource<Int, ResultCharacter>() {

    override fun getRefreshKey(state: PagingState<Int, ResultCharacter>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, ResultCharacter> {

        return try {
            //pagina inicial
            val currentPage = params.key ?: 1
            //servicio api a consumir para obtener los datos
            val response = apiService.getAllCharacter(currentPage)
            //obtenemos los datos de la respuesta en el modelo
            val responseData = mutableListOf<ResultCharacter>()
            //si el response no es null y tiene datos agregar si no emptylist
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}