package com.example.todolist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.viewmodels.ProjectsViewModel
import com.example.todolist.viewmodels.ProjectsViewModelFactory
import com.example.todolist.App
import com.example.todolist.databinding.FragmentProjectsBinding
import com.example.todolist.ui.projectRV.ProjectsItemTouchHelperCallback
import com.example.todolist.ui.projectRV.ProjectsRVAdapter
import com.example.todolist.ui.projectRV.VerticalSpaceItemDecoration
import kotlinx.coroutines.launch

const val TAG="ProjectsFragmentClass"
const val TAG1="CheckingCreation"
class ProjectsFragment : Fragment() {

    val viewModel: ProjectsViewModel by viewModels { ProjectsViewModelFactory((requireActivity().application as App).projectsRepository) }
    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!
    private val projectAdapter = ProjectsRVAdapter()
    private val callback: ProjectsItemTouchHelperCallback =ProjectsItemTouchHelperCallback(projectAdapter)
    private val touchHelper = ItemTouchHelper(callback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.initialize()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.projectsListFlow.flowWithLifecycle(
                viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED
            ).collect {
                projectAdapter.submit(it,binding.rvProjects)
            }
        }


        projectAdapter.onProjectClickListener = {
            findNavController().navigate(
                ProjectsFragmentDirections.actionProjectsFragmentToTaskListFragment(
                    it.id
                )
            )
        }


        with(binding.rvProjects) {
            touchHelper.attachToRecyclerView(this)
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(requireContext())
                .apply {
                    addItemDecoration(
                        VerticalSpaceItemDecoration(50)
                    )
                }
        }

        projectAdapter.onProjectSwipeListener={
                viewModel.deleteNewProject(it)
        }


    }
}