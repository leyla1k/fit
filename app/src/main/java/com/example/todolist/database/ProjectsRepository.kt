package com.example.todolist.database

import com.example.todolist.TOKEN
import com.example.todolist.entities.Training
import com.example.todolist.entities.ProjectEntity
import com.example.todolist.entities.UserDTO
import com.example.todolist.fromProjectsEntityToProjects
import com.example.todolist.remote.MyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProjectsRepository(val dao: ProjectsDao, val myApi: MyApi) {
    suspend fun getAllProjectsFlow(): Flow<List<Training>> {
        val list = dao.getAllProjectsFlowEntity()
        return list.map {
            it.map { uit ->
                fromProjectsEntityToProjects(uit)
            }
        }
    }


    suspend fun addNewProject(projectEntity: ProjectEntity) {
        withContext(Dispatchers.IO) {
            dao.insertNewProject(projectEntity)
        }
    }

    suspend fun deleteNewProject(projectEntity: ProjectEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteNewProject(projectEntity)
        }
    }



    suspend fun register(pass:String,name:String){
        withContext(Dispatchers.IO) {
            if(TOKEN == "USER_TOKEN"){
                myApi.registerUser(UserDTO(password = pass, name = name))
            }else{
                myApi.registerTrainer(UserDTO(password = pass, name = name))
            }
        }
    }

    suspend fun login(pass:String,name:String){
        withContext(Dispatchers.IO) {
            if(TOKEN == "USER_TOKEN"){
                myApi.loginUser(UserDTO(password = pass, name = name))
            }else{
                myApi.loginTrainer(UserDTO(password = pass, name = name))
            }
        }
    }
}