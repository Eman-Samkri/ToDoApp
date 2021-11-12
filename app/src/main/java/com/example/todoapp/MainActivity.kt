package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todoapp.HomeFragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private val homeFragment = HomeFragment()
private val TaskFragment = com.example.todoapp.taskFragment.TaskFragment()
private val ListFragment = TaskListFragment()

private lateinit var bottomNavigationView: BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        replaceFragment(homeFragment)



        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.HomeFragment -> replaceFragment(homeFragment)
                R.id.TaskFragment -> replaceFragment(TaskFragment)
                R.id.TaskListFragment -> replaceFragment(ListFragment)
            }
            true
            }



    }

      fun replaceFragment(fragment: Fragment){

        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container,fragment)
                transaction.commit()
        }
    }



}