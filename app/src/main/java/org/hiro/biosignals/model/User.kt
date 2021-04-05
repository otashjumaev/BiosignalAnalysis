package org.hiro.biosignals.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val createdTime: Long
)
