package com.example.todolist.entities

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("health_description") val healthDescription: String = "",
    @SerializedName("id") val id: Int = 0,
    @SerializedName("mail") val mail: String="",
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("phone") val phone: String="",
    @SerializedName("trainings") val trainings: List<Training>? = null
)