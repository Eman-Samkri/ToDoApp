package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TaskDao {

        @Query("SELECT * FROM Task")
        fun getAllTask(): LiveData<List<Task>>

        @Query("SELECT * FROM Task WHERE id= (:id)")
        fun getTask(id: Int): LiveData<Task?>

        @Update
        fun updateTask(task: Task)

        @Insert
        fun addTask(task:Task)

        @Query("DELETE FROM task WHERE id =:id")
        fun deleteTask(id: Int)

        @Query("UPDATE task SET isCompleted = :isComplete WHERE id =:id")
        fun updateIsCompleted(isComplete: Boolean, id: Int)


}