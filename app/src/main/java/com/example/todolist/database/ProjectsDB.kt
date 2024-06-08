package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.entities.MemoryEntity
import com.example.todolist.entities.ProjectEntity

@Database(entities = [ProjectEntity::class, MemoryEntity::class], version = 1, exportSchema = false)
abstract class ProjectsDB : RoomDatabase(){

    abstract fun getProjectDao(): ProjectsDao
    abstract fun getMemoryDao(): MemoryDao

    companion object {
        @Volatile
        private var INSTANCE: ProjectsDB? = null

        fun getDatabase(context: Context): ProjectsDB {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectsDB::class.java,
                    "projects_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}