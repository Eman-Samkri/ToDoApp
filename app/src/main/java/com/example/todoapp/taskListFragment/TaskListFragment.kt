package com.example.todoapp

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.HomeFragment.KEY_HEALTH
import com.example.todoapp.HomeFragment.KEY_PERSONAL
import com.example.todoapp.HomeFragment.KEY_SOCIAL
import com.example.todoapp.HomeFragment.KEY_WORK
import com.example.todoapp.database.Task
import com.example.todoapp.taskFragment.TaskFragment
import com.example.todoapp.taskListFragment.TaskListViewModel
import java.util.*

const val KEY_ID = "myTaskId"
const val KEY_ID_UPDATE = "myTaskId for Update btn"
const val KEY_IS_COMPLETED = "is completed"

class TaskListFragment : Fragment() {

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var nothingToShow : ImageView
    private lateinit var nothingToShowText:TextView
    private var task = listOf<Task>()
    val currentDate = Date()

    val args = Bundle()


    private val taskListViewModel by lazy { ViewModelProvider(this).get(TaskListViewModel::class.java) }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_bar,menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.due_date_menu_id -> {
                updateUI(task.filterNot { it.due_date == null }.sortedBy { it.due_date })
                true
            }
            R.id.alpha_id -> {
                updateUI(task.sortedBy {it.title})
                true
            }
            R.id.creation_date_id -> {
                updateUI(task.sortedBy {it.creation_date})
                true
            }

            R.id.last_creation_date_menu_id -> {
                updateUI(task.sortedByDescending {it.creation_date})
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list,container,false)

        taskRecyclerView = view.findViewById(R.id.tasks_recycler_Id)
        nothingToShow = view.findViewById(R.id.nothing_id)
        nothingToShowText = view.findViewById(R.id.nothingTv_id)

        val  linearLayoutManager = LinearLayoutManager(context)
        taskRecyclerView.layoutManager = linearLayoutManager
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListViewModel.liveDataTask.observe(
            viewLifecycleOwner, Observer {

                task = it.filterNot { it.isCompleted }
                if(task.isEmpty()){
                    taskRecyclerView.visibility = View.GONE
                }else {
                    taskRecyclerView.visibility = View.VISIBLE
                }

                updateUI(task)

                val taskWork = arguments?.getInt(KEY_WORK,1)
                val taskPersonal = arguments?.getString(KEY_PERSONAL)
                val taskHealth = arguments?.getString(KEY_HEALTH)
                val taskSocial = arguments?.getString(KEY_SOCIAL)

                if (taskWork!=null) {
                    setMenuVisibility(false)
                    updateUI(task.filter { it.category ==1 })
                }
                if (taskPersonal !=null){
                    setMenuVisibility(false)
                    updateUI(task.filter { it.category ==2 })
                }
                if (taskHealth !=null){
                    setMenuVisibility(false)
                    updateUI(task.filter { it.category ==3 })
                }
                if (taskSocial !=null){
                    setMenuVisibility(false)
                    updateUI(task.filter { it.category ==4 })
                }



            }
        )
        swapItem()

    }



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
                        val currentTask = task[viewHolder.adapterPosition]
                        taskListViewModel.deleteTask(currentTask.id)
                        Toast.makeText(activity, "Task deleted", Toast.LENGTH_SHORT).show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        val currentTask = task[viewHolder.adapterPosition]

                        args.putString(KEY_IS_COMPLETED,"false")
                        args.putInt(KEY_ID,currentTask.id)
                        val fragment = TaskFragment()
                        fragment.arguments = args

                        activity?.let {
                            it.supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container,fragment)
                                .commit()
                        }

                        Toast.makeText(activity, "Task completed", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }).attachToRecyclerView(taskRecyclerView)
    }


    private fun updateUI(tasks: List<Task>) {
        val taskAdapter = Adapter(tasks)
        if (tasks.isEmpty())  {
            nothingToShow.visibility = View.VISIBLE
            nothingToShowText.visibility = View.VISIBLE
        }else{
            taskRecyclerView.adapter = taskAdapter

        }
    }




    private inner class ViewHolder(view:View): RecyclerView.ViewHolder(view) , View.OnClickListener{


        lateinit var task: Task
        private val titleTv: TextView = itemView.findViewById(R.id.editTextTitle_id)
        private val descriptionTv: TextView = itemView.findViewById(R.id.editTextDescription_id)
        private val category: TextView = itemView.findViewById(R.id.catogory_id)
        private val type: View = itemView.findViewById(R.id.type_id)
        private val due_date_alart :View = itemView.findViewById(R.id.due_date_alert)


        fun bind(task: Task) {
            this.task = task
            titleTv.text = task.title
            descriptionTv.text = task.Description
            type.setBackgroundResource(getColor(task.category))
            category.text = getType(task.category)

            compareDate()
        }



        private fun getColor(category: Int):Int {
            return when (category) {
                1 -> R.color.work
                2 -> R.color.personal
                3 -> R.color.health
                4 -> R.color.social
                else -> R.color.work
            }
        }

        private fun getType(category: Int):String {
            return when (category) {
                1 -> "Work"
                2 -> "Personal"
                3 -> "Health"
                4 -> "Social"
                else -> "Work"
            }
        }

        private fun compareDate(){
            task.due_date?.let {
                if (currentDate.after(it))
                {
                    due_date_alart.setBackgroundResource(R.color.due_date_co)
                }
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
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