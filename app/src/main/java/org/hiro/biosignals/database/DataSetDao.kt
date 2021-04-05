package org.hiro.biosignals.database

import androidx.lifecycle.LiveData
import androidx.room.*
import org.hiro.biosignals.model.DataSet

@Dao
interface DataSetDao {

    @Insert
    fun insert(dataset: DataSet): Long

    @Update
    fun update(dataset: DataSet)

    @Delete
    fun delete(dataset: DataSet)

    @Query("SELECT * FROM DataSet WHERE id = :id")
    fun get(id: Long): DataSet?

    @Query("SELECT * FROM DataSet ORDER BY importedTime DESC")
    fun getDataSet(): List<DataSet>

    @Query("SELECT * FROM DataSet WHERE userId = :userID")
    fun getDataSetByUser(userID: Long): LiveData<List<DataSet>>

    @Query("DELETE FROM DataSet")
    fun clear()
}