package com.example.todolist.remote

import com.example.todolist.entities.User
import com.example.todolist.entities.UserDTO
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface MyApi {

//http://localhost:8000/login

    @POST("login/user")
    suspend fun loginUser(@Body userDTO: UserDTO)

    @POST("register/user")
    suspend fun registerUser(@Body userDTO: UserDTO)

    @POST("login/trainer")
    suspend fun loginTrainer(@Body userDTO: UserDTO)

    @POST("register/trainer")
    suspend fun registerTrainer(@Body userDTO: UserDTO)

 /*   @GET("register/trainer")
    suspend fun getSceduleMain():Response<UserDTO>

    @GET("register/trainer")
    suspend fun getSceduleMain():Response<UserDTO>*/



}