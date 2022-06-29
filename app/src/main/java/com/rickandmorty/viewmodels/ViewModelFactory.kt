package com.rickandmorty.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rickandmorty.repositories.CharacterRepository


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(var app: Application, val repository: CharacterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(app,repository) as T
        }
        throw IllegalArgumentException("Unkow viemodel class")
    }

}