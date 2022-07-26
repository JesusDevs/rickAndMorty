package com.rickandmorty.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rickandmorty.repositories.CharacterRepository


class ViewModelFactory(var app: Application, private val repository: CharacterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(app,repository) as T
        }
        throw IllegalArgumentException("Unkow viemodel class")
    }

}