package org.hiro.biosignals.database

import androidx.lifecycle.LiveData
import androidx.room.*
import org.hiro.biosignals.model.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User): Long

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    fun get(id: Long): User?

    @Query("SELECT * FROM User ORDER BY createdTime DESC")
    fun getUsers(): LiveData<List<User>>

    @Query("DELETE FROM User")
    fun clear()

}