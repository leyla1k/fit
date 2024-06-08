package com.example.todolist.ui.projectRV

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemProjectBinding
import com.example.todolist.entities.Training
import java.util.Collections


class ProjectsRVAdapter() : ListAdapter<Training, ProjectsViewHolder>(ProjectsDiffCallback()),
    ProjectsItemTouchHelperAdapter {
    var onProjectSwipeListener: ((Training) -> Unit)?=null
    var onProjectClickListener: ((Training) -> Unit)? = null
    lateinit var trainings: MutableList<Training>
    lateinit var itemProjectBinding: ItemProjectBinding

    fun submit(list:  List<Training>, rv: RecyclerView) {
        trainings=list.toMutableList()
        submitList(trainings){
            rv.invalidateItemDecorations()
        }//иначе добавление нового элемента - проблема
    }

    override fun onItemDismiss(position: Int) {
        onProjectSwipeListener?.invoke(trainings[position])
        notifyItemRemoved(position)
        trainings.removeAt(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(trainings, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(trainings, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        itemProjectBinding = ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectsViewHolder(itemProjectBinding)
    }


    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
        with(holder.binding) {
            containerProject.setOnClickListener() {
                onProjectClickListener?.invoke(item)
            }
        }

    }

}