package com.example.todoapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.HomeFragment.KEY_PERSONAL
import com.example.todoapp.HomeFragment.KEY_WORK
import com.example.todoapp.database.Task
import com.example.todoapp.taskFragment.TaskFragment
import com.example.todoapp.taskListFragment.TaskListViewModel
import java.util.*

const val KEY_ID = "myTaskId"
const val KEY_ID_UPDATE = "myTaskId"

class TaskListFragment : Fragment() {

    private lateinit var taskRecyclerView: RecyclerView
    private var Task = listOf<Task>()
   // private lateinit var task: Task
    val current_date = Date()


    private val taskListViewModel by lazy { ViewModelProvider(this).get(TaskListViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list,container,false)

        taskRecyclerView = view.findViewById(R.id.tasks_recycler_Id)
        val  linearLayoutManager = LinearLayoutManager(context)
        taskRecyclerView.layoutManager = linearLayoutManager
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListViewModel.liveDataTask.observe(
            viewLifecycleOwner, Observer {
                //updateUI(it)
                Task = it
                if(it.isEmpty()){
                    taskRecyclerView.visibility = View.GONE
                }else {
                    taskRecyclerView.visibility = View.VISIBLE
                }

                updateUI(it)

                val taskId = arguments?.getInt(KEY_WORK,1)
                val taskId2 = arguments?.getString(KEY_PERSONAL)
                if (taskId!=null) {
                    updateUI(it.filter { it.category ==2 })
                }
                if (taskId2 !=null){
                    updateUI(it.filter { it.category ==1 })
                }

            }
        )



        swapItem()

    }



//    override fun onOptionsItemSelected(item: MenuItem){
//        return when(item.itemId){
//            R.id.due_date_id -> {
//                Task.sortedBy { it.due_date }
//            }
//
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    private fun swapItem() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        val currentTask = Task[viewHolder.adapterPosition]
                        taskListViewModel.deleteTask(currentTask.id)
                        Toast.makeText(activity, "Task deleted", Toast.LENGTH_SHORT).show()
                    }
                    ItemTouchHelper.RIGHT -> {

                        val currentTask = Task[viewHolder.adapterPosition]
                        taskListViewModel.deleteTask(currentTask.id)
                        Toast.makeText(activity, "Task completed", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }).attachToRecyclerView(taskRecyclerView)
    }


    private fun updateUI(tasks: List<Task>) {
        val taskAdapter = Adapter(tasks)
        taskRecyclerView.adapter = taskAdapter
    }




    private inner class ViewHolder(view:View): RecyclerView.ViewHolder(view) , View.OnClickListener{


        lateinit var task: Task
        private val titleTv: TextView = itemView.findViewById(R.id.editTextTitle_id)
        private val descriptionTv: TextView = itemView.findViewById(R.id.editTextDescription_id)
        private val category: TextView = itemView.findViewById(R.id.catogory_id)
        private val type: View = itemView.findViewById(R.id.type_id)
        private val due_date_alart :View = itemView.findViewById(R.id.due_date_aert)


        fun bind(task: Task) {
            this.task = task
             titleTv.text = task.title
            descriptionTv.text = task.Description
            // titleTv.setTextColor(context.resources.getColor(getColor(task.priority)))
            type.setBackgroundResource(getColor(task.category))
            category.text = getType(task.category)
            compareDate()


        }



        private fun getColor(category: Int):Int {
            return when (category) {
                0 -> R.color.purple_700
                1 -> R.color.purple_200
                2 -> R.color.purple_500
                else -> R.color.teal_200
            }
        }

        private fun getType(category: Int):String {
            return when (category) {
                0 -> "Work"
                1 -> "Personal"
                2 -> "Health"
                else -> "Work"
            }
        }

        private fun compareDate(){
            task.due_date?.let {
                if (current_date.after(it))
                {
                    due_date_alart.setBackgroundResource(getColor(0))
                }
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("eman","eman")
           if (v == itemView ){
                val args = Bundle()
                args.putInt(KEY_ID,task.id)
               args.putInt(KEY_ID_UPDATE,task.id)

               val fragment = TaskFragment()
                fragment.arguments = args


                activity?.let {
                    it.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,fragment)
                        .commit()
                }

            }
        }
    }

    private inner class Adapter(var tasks:List<Task>) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_list,parent,false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val task = tasks[position]

           holder.bind(task)
        }

        override fun getItemCount(): Int = tasks.size



    }



}