package com.rickandmorty.model.pojo



import androidx.room.Entity
import com.google.gson.annotations.SerializedName


data class CharacterResponse(
    @SerializedName("info")
    val info: Info,

    @SerializedName("results")
    val results: List<Result>
)