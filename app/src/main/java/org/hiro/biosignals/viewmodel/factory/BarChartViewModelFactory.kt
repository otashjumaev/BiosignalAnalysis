package org.hiro.biosignals.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.hiro.biosignals.viewmodel.BarChartViewModel

class BarChartViewModelFactory(private val data:String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BarChartViewModel::class.java))
            return BarChartViewModel(data) as T
        throw IllegalArgumentException("ERROR ARGUMENT TYPE")
    }
}