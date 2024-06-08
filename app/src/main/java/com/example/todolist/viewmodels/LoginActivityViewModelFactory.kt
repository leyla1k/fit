package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.ProjectsRepository

class LoginActivityViewModelFactory( private
                                     val projectsRepository: ProjectsRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return LoginViewModel(projectsRepository) as T
    }
}