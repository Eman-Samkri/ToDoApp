package com.example.todoapp.HomeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.KEY_ID
import com.example.todoapp.R
import com.example.todoapp.TaskListFragment
import com.example.todoapp.database.Task
import com.example.todoapp.taskFragment.TaskFragment
import com.example.todoapp.taskFragment.TaskFragmentViewModel

const val KEY_WORK = "kdk"
const val KEY_PERSONAL ="KEY personal"

class HomeFragment : Fragment() {
    private lateinit var workBtn:Button
    private lateinit var personalBtn:Button

    private val fragmentHomeViewModel by lazy { ViewModelProvider(this)
        .get(HomeFragmentViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        workBtn = view.findViewById(R.id.work_id_home)
        personalBtn=view.findViewById(R.id.personal_id_home)


        return view
    }

    private fun replaceFragment(fragment: Fragment){


        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()

        val args = Bundle()
        workBtn.setOnClickListener {
            args.putInt(KEY_WORK,1)
            val fragment = TaskListFragment()
            fragment.arguments = args
            replaceFragment(fragment)

        }

        personalBtn.setOnClickListener {
            args.putString(KEY_PERSONAL,"kk")
            val fragment = TaskListFragment()
            fragment.arguments = args
            replaceFragment(fragment)
        }
    }





}