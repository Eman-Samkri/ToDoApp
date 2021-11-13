package com.example.todoapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(
    var title:String ="",
    var Description:String ="",
    var category:Int = 1,
    var due_date:Date? = null,
    var isCompleted:Boolean = false,
    val creation_date: Date = Date()){@PrimaryKey(autoGenerate = true) var id: Int = 0}
