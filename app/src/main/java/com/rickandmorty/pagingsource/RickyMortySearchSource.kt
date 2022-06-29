package com.rickandmorty.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmorty.model.pojo.ResultCharacter
import com.rickandmorty.remote.ICharacterService
import retrofit2.HttpException

private const val START_PAGE = 1

class RickyMortySearchSource(
    private val apiService: ICharacterService,
    private val query: String
    ) : PagingSource<Int, ResultCharacter>() {



    override fun getRefreshKey(state: PagingState<Int, ResultCharacter>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, ResultCharacter> {

        return try {
            //pagina inicial
            val currentPage = params.key ?: START_PAGE
            //servicio api a consumir para obtener los datos se le pasa query y numero de pagina
            val response = apiService.searchAllCharacter(query,currentPage )
            //obtenemos los datos de la respuesta en el modelo
            val responseData = mutableListOf<ResultCharacter>()
            //si el response no es null y tiene datos agregar si no emptylist
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)
            Log.d("RickyMortyPagingSource", "load: ${responseData}")
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == START_PAGE) null else -1,
                nextKey = if (response.body()?.info?.next == null) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e : HttpException) {
            LoadResult.Error(e)
        }
    }
}