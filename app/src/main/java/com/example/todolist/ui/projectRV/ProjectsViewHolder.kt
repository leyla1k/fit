package com.example.todolist.ui.projectRV

import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.entities.Training
import com.example.todolist.databinding.ItemProjectBinding
import java.text.DateFormat

class ProjectsViewHolder(val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: Training) {
        with(binding) {
    textViewName.text = item.name
    textViewDate.text =  DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(
        item.date!!.time)

        }
    }
}