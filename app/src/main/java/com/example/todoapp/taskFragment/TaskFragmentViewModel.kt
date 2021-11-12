package com.example.todoapp.taskFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todoapp.database.Task
import com.example.todoapp.database.TaskRepository
import java.util.*

class TaskFragmentViewModel : ViewModel(){

    private val taskRepository = TaskRepository.get()
    private val taskIdLiveData = MutableLiveData<Int>()



    var taskLiveData: LiveData<Task?> =
        Transformations.switchMap(taskIdLiveData){
            taskRepository.getTask(it)
        }



    fun loadTask(taskId: Int){
        taskIdLiveData.value = taskId
    }



    fun addTask(task: Task){
        taskRepository.addTask(task)
    }


    fun saveUpdate(task:Task){
        taskRepository.updateTask(task)
    }



}