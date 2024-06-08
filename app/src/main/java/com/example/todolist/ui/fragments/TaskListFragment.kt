package com.example.todolist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todolist.databinding.FragmentTaskListBinding
import com.example.todolist.entities.User
import com.example.todolist.ui.taskRV.TaskItemTouchHelperCallback
import com.example.todolist.ui.taskRV.TaskRVAdapter
import com.example.todolist.ui.taskRV.TaskSpaceItemDecoration

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    private val projectAdapter = TaskRVAdapter()
    private val callback: TaskItemTouchHelperCallback = TaskItemTouchHelperCallback(projectAdapter)
    private val touchHelper = ItemTouchHelper(callback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectAdapter.submit(
            listOf(
                User(
                    password = "q",
                    name = "w"
                ),

            ), binding.rvTask
        )

        with(binding.rvTask) {
            touchHelper.attachToRecyclerView(this)
            adapter = projectAdapter
            layoutManager = GridLayoutManager(requireContext(),2 )
                .apply {
                    addItemDecoration(
                        TaskSpaceItemDecoration(20)
                    )
                }
        }
    }

}