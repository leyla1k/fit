package com.example.todolist.entities

import java.util.Date

data class Training (
    val id: String,
    val name:String,
    val date: Date?,
    var isCompleted: Boolean
         )