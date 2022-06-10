package com.rickandmorty.model.pojo


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "all_character_table" )
data class Result(
    @SerializedName("created")
    val created: String,
    @SerializedName("gender")
    val gender: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)