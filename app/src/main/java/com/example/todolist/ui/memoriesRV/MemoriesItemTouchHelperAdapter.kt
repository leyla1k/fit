package com.example.todolist.ui.memoriesRV

interface MemoriesItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onItemDismiss(position: Int)

}