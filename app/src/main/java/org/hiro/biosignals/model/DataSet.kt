package org.hiro.biosignals.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataSet(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val userId: Long,
    var fileName: String,
    val dataSize: Int,
    val importedTime: Long,
    val filePath: String
)