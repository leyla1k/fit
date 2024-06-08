package com.example.todolist

import android.app.Application
import android.util.Log
import com.example.todolist.database.ProjectsDB
import com.example.todolist.database.ProjectsRepository
import com.example.todolist.remote.MyApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.yandex.mapkit.MapKitFactory
import java.util.concurrent.TimeUnit

const val TAG="AppClass"
class App:Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("538930ab-c7e4-4077-9945-8c8e070a4de7")
    }

    val myApi: MyApi by lazy {
        retrofitMain.create(MyApi::class.java)
    }

    private val retrofitMain by lazy {
        Retrofit.Builder().client(clientMain).baseUrl("http://158.160.62.249:8000/")
        .addConverterFactory(GsonConverterFactory.create()).build()}

    val clientMain by lazy{  OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $TOKEN")
            .build()
        chain.proceed(newRequest)
    }).build() }



    private val projectsDatabase by lazy{ ProjectsDB.getDatabase(applicationContext) }
    val projectsRepository by lazy{
        ProjectsRepository(projectsDatabase.getProjectDao(),myApi)
    }.also {  Log.d(TAG, " again!") }

    val daoMemory by lazy{
        projectsDatabase.getMemoryDao()
    }


}