package com.example.todolist.ui.taskRV

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTaskBinding

import com.example.todolist.entities.User


import java.util.Collections


class TaskRVAdapter() : ListAdapter<User, TaskProjectsViewHolder>(TaskDiffCallback()),
    TaskItemTouchHelperAdapter {

    var onProjectClickListener: ((User) -> Unit)? = null
    lateinit var projects: MutableList<User>
    lateinit var itemTaskBinding: ItemTaskBinding

    fun submit(list:  List<User>, rv: RecyclerView) {
        projects=list.toMutableList()
        submitList(list){
            rv.invalidateItemDecorations()
        }//иначе добавление нового элемента - проблема
    }

    override fun onItemDismiss(position: Int) {
        projects.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(projects, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(projects, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskProjectsViewHolder {
        itemTaskBinding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TaskProjectsViewHolder(itemTaskBinding)
    }


    override fun onBindViewHolder(holder: TaskProjectsViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)


    }

}