package com.example.todolist.ui.taskRV

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.entities.User

class TaskDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User) =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: User, newItem: User) =
        oldItem == newItem
}