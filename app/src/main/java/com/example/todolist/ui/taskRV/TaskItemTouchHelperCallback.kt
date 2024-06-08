package com.example.todolist.ui.taskRV

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todolist.ui.taskRV.TaskItemTouchHelperAdapter


class TaskItemTouchHelperCallback: ItemTouchHelper.Callback/*, Swipable*/ {

    //override var onProjectSwipeListener: ((Int) -> Unit)?=null

    private var mAdapter: TaskItemTouchHelperAdapter? = null

    constructor(adapter: TaskItemTouchHelperAdapter?) {
        mAdapter = adapter
    }
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        mAdapter!!.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }



    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        mAdapter!!.onItemDismiss(viewHolder.adapterPosition)
       // Log.d("SwipableRight", "onViewCreated: ")
       // onProjectSwipeListener?.invoke(viewHolder.adapterPosition)
    }


}