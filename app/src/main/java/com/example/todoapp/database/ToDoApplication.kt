package com.example.todoapp.database

import android.app.Application

class ToDoApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        TaskRepository.initialize(this)
    }
}