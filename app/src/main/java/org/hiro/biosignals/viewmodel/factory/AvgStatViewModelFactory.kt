package org.hiro.biosignals.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.hiro.biosignals.viewmodel.AvgStatViewModel

class AvgStatViewModelFactory(private val list: MutableList<String?>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AvgStatViewModel::class.java))
            return AvgStatViewModel(list) as T
        throw IllegalArgumentException("ERROR ARGUMENT TYPE")
    }
}