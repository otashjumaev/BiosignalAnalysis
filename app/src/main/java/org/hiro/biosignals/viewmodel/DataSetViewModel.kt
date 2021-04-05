package org.hiro.biosignals.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.hiro.biosignals.database.DataSetDao
import org.hiro.biosignals.databinding.ItemDataSetBinding
import org.hiro.biosignals.model.DataSet
import org.hiro.biosignals.util.formatTime
import java.util.*

class DataSetViewModel(val db: DataSetDao, private val userId: Long) : ViewModel() {
    val dataList = db.getDataSetByUser(userId)

    fun saveDataSet(name: String, size: Int, absolutePath: String) = db.insert(
        DataSet(
            fileName = name,
            dataSize = size,
            importedTime = Date().time,
            filePath = absolutePath,
            userId = userId
        )
    )


    fun dataSetAdapterDelegate(itemClickedListener: (DataSet) -> Unit) =
        adapterDelegateViewBinding<DataSet, DataSet, ItemDataSetBinding>(
            { layoutInflater, root -> ItemDataSetBinding.inflate(layoutInflater, root, false) }
        ) {
            binding.layoutCont.setOnClickListener {
                itemClickedListener(item)
            }
            bind {
                binding.fileNameTxt.text = item.fileName
                binding.dataSizeTxt.text = item.dataSize.toString()
                binding.timeTxt.text = formatTime(item.importedTime)
            }
        }
}