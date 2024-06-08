package com.example.todolist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.entities.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectsDao {

    @Query("SELECT * FROM projects")
    suspend fun getAllListProjects(): List<ProjectEntity?>?

    @Query("SELECT * FROM projects")
    fun getAllProjectsFlowEntity(): Flow<List<ProjectEntity>>

    @Insert
    suspend fun insertNewProject(projectEntity: ProjectEntity)
    @Update
    suspend fun updateNewProject(projectEntity: ProjectEntity)


    @Delete
    suspend fun deleteNewProject(projectEntity: ProjectEntity)
}