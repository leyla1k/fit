package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.database.ProjectsRepository
import com.example.todolist.entities.Training
import com.example.todolist.fromProjectsToProjectsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TAG = "ProjectsViewModelClass"

class ProjectsViewModel(val projectsRepository: ProjectsRepository) : ViewModel() {

    /* suspend fun addNewProject(project: Project){
         projectsRepository.addNewProject(fromProjectsToProjectsEntity(project))
     }*/

    private val _projectsListFlow = MutableStateFlow<MutableList<Training>>(arrayListOf())
    val projectsListFlow: StateFlow<MutableList<Training>> = _projectsListFlow.asStateFlow()

    init {

        viewModelScope.launch {
            projectsRepository.getAllProjectsFlow().collect { uit ->
                _projectsListFlow.update {
                    mutableListOf<Training>().apply {
                        addAll(uit.map { noteData ->
                            noteData.copy()
                        })
                    }
                }
            }
        }
    }

    fun initialize() {

    }

    fun deleteNewProject(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.deleteNewProject(fromProjectsToProjectsEntity(training))
        }
    }
}