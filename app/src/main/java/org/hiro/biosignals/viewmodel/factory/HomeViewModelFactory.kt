package org.hiro.biosignals.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.hiro.biosignals.viewmodel.HomeViewModel

class HomeViewModelFactory(private val path: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(path) as T
        throw IllegalArgumentException("ERROR ARGUMENT TYPE")
    }
}