package org.hiro.biosignals.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.hiro.biosignals.database.DataSetDao
import org.hiro.biosignals.viewmodel.DataSetViewModel

class DataSetViewModelFactory(private val db: DataSetDao, private val id: Long) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataSetViewModel::class.java))
            return DataSetViewModel(db, id) as T
        throw IllegalArgumentException("ERROR ARGUMENT TYPE")
    }
}