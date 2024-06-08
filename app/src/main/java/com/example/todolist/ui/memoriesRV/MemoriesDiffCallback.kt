package com.example.todolist.ui.memoriesRV

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.entities.Memory

class MemoriesDiffCallback : DiffUtil.ItemCallback<Memory>() {

    override fun areItemsTheSame(oldItem: Memory, newItem: Memory) =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Memory, newItem: Memory) =
        oldItem == newItem
}