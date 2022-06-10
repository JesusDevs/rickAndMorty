package com.rickandmorty.model.pojo


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)