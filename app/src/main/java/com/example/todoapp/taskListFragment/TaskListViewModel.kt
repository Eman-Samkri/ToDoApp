package com.example.todoapp.taskListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.database.Task
import com.example.todoapp.database.TaskRepository

class TaskListViewModel : ViewModel(){

    val  taskRepository  =  TaskRepository.get()

    val liveDataTask = taskRepository.getAllTask()

    fun deleteTask(id: Int){
        taskRepository.deleteTask(id)
    }

    fun saveUpdate(task:Task){
        taskRepository.updateTask(task)
    }






}