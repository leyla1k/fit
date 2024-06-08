package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import com.example.todolist.database.ProjectsRepository

class LoginViewModel(val repository: ProjectsRepository):ViewModel() {

    suspend fun register(pass:String,name:String){
        repository.register(pass,name)
    }

    suspend fun login(pass:String,name:String){
        repository.login(pass,name)
    }

}