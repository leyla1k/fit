package com.example.todolist.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "projects")
data class ProjectEntity (
    @PrimaryKey
    val id: String,
    val name:String,
    val date: Long?,
    var isCompleted: Boolean
)