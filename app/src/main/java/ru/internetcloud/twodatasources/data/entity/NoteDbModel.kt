package ru.internetcloud.twodatasources.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val text: String = ""
)
