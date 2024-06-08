package com.example.todolist.ui.memoriesRV

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemMemoryBinding
import com.example.todolist.entities.Memory
import java.util.Collections


class MemoriesRVAdapter() : ListAdapter<Memory, MemoriesViewHolder>(MemoriesDiffCallback()),
    MemoriesItemTouchHelperAdapter {
    var onProjectSwipeListener: ((Memory) -> Unit)?=null
    var onProjectClickListener: ((Memory) -> Unit)? = null
    lateinit var projects: MutableList<Memory>
    lateinit var itemMemoryBinding: ItemMemoryBinding

    fun submit(list:  List<Memory>, rv: RecyclerView) {
        projects=list.toMutableList()
        submitList(projects){
            rv.invalidateItemDecorations()
        }//иначе добавление нового элемента - проблема
    }

    override fun onItemDismiss(position: Int) {
        onProjectSwipeListener?.invoke(projects[position])
        notifyItemRemoved(position)
        projects.removeAt(position)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoriesViewHolder {
        itemMemoryBinding = ItemMemoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoriesViewHolder(itemMemoryBinding)
    }


    override fun onBindViewHolder(holder: MemoriesViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
        with(holder.binding) {
            containerProject.setOnClickListener() {
                onProjectClickListener?.invoke(item)
            }
        }

    }

}