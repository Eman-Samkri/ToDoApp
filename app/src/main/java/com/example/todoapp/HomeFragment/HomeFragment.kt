package com.example.todoapp.HomeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.KEY_ID
import com.example.todoapp.R
import com.example.todoapp.TaskListFragment
import com.example.todoapp.database.Task
import com.example.todoapp.taskFragment.TaskFragment
import com.example.todoapp.taskFragment.TaskFragmentViewModel

const val KEY_WORK = "kdk"
const val KEY_PERSONAL ="KEY personal"
const val KEY_HEALTH ="KEY HEALTH"
const val KEY_SOCIAL ="KEY FUN"
class HomeFragment : Fragment() {
    private lateinit var workBtn:Button
    private lateinit var personalBtn:Button
    private lateinit var healthBtn :Button
    private lateinit var socialBtn :Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        workBtn = view.findViewById(R.id.work_id_home)
        personalBtn=view.findViewById(R.id.personal_id_home)
        healthBtn = view.findViewById(R.id.health_id_home)
        socialBtn = view.findViewById(R.id.social_id_home)


        return view
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
            args.putString(KEY_PERSONAL,"personal")
            val fragment = TaskListFragment()
            fragment.arguments = args
            replaceFragment(fragment)
        }

        healthBtn.setOnClickListener {
            args.putString(KEY_HEALTH,"HEALTH")
            val fragment = TaskListFragment()
            fragment.arguments = args
            replaceFragment(fragment)
        }

        socialBtn.setOnClickListener {
            args.putString(KEY_SOCIAL,"SOCIAL")
            val fragment = TaskListFragment()
            fragment.arguments = args
            replaceFragment(fragment)
        }
    }



    private fun replaceFragment(fragment: Fragment){


        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit()
        }
    }

}