package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*


@Dao
interface TaskDao {

        @Query("SELECT * FROM Task")
        fun getAllTask(): LiveData<List<Task>>

        @Query("SELECT * FROM Task WHERE id= (:id)")
        fun getTask(id: Int): LiveData<Task?>

        @Query("SELECT * FROM Task WHERE category= (:category)")
        fun getTaskByWork(category: Int): LiveData<List<Task>>

        @Update
        fun updateTask(task: Task)

        @Insert
        fun addTask(task:Task)

        @Query("DELETE FROM task WHERE id =:id")
        fun deleteTask(id: Int)


}