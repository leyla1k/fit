package com.example.todolist

import com.example.todolist.entities.Training
import com.example.todolist.entities.ProjectEntity
import java.util.Date



fun fromProjectsToProjectsEntity(training: Training):ProjectEntity {
    val inputDate = training.date
   return ProjectEntity(
        id = training.id,
        name = training.name,
        date = inputDate?.time ,
        isCompleted = training.isCompleted
    )

}


fun fromProjectsEntityToProjects(projectEntity: ProjectEntity):Training {
    val inputDate = projectEntity.date
    return Training(
        id = projectEntity.id,
        name = projectEntity.name,
        date =  inputDate?.let { Date(it) },
        isCompleted = projectEntity.isCompleted
    )

}