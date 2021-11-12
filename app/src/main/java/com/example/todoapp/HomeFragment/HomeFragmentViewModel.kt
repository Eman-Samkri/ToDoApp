package com.example.todoapp.HomeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todoapp.database.Task
import com.example.todoapp.database.TaskRepository

class HomeFragmentViewModel : ViewModel() {

    private val taskCategoryLiveData = MutableLiveData<Int>()
    private val taskRepository = TaskRepository.get()


//    var taskLiveData: LiveData<List<Task>> =
//        Transformations.switchMap(taskCategoryLiveData){
//            taskRepository.getTaskByWork(it)
//        }

 fun byCategory (category: Int) : LiveData<List<Task>>{
    return taskRepository.getTaskByWork(category)
 }


}