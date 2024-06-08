package com.example.todolist.entities

data class User (val healthDescription: String = "",
                 val id: Int = 0,
                 val mail: String = "",
                 val name: String,
                 val password: String,
                 val phone: String="",
                 val trainings:List<Training>? = null
                 )