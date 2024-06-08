package com.example.todolist.ui.projectRV

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.entities.Training

class ProjectsDiffCallback : DiffUtil.ItemCallback<Training>() {

    override fun areItemsTheSame(oldItem: Training, newItem: Training) =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Training, newItem: Training) =
        oldItem == newItem
}