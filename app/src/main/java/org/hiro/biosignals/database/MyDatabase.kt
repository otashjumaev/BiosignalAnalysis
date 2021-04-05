package org.hiro.biosignals.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.hiro.biosignals.model.DataSet
import org.hiro.biosignals.model.User

@Database(entities = [DataSet::class, User::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract val datasetDao: DataSetDao
    abstract val userDao: UserDao

    companion object {
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "DB"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}