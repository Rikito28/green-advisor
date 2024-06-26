package com.riski.greenadvisor.utils

import androidx.recyclerview.widget.DiffUtil
import com.riski.greenadvisor.data.response.DataAnyItem

class DiffUtilsAnyPlant(private val oldList: List<DataAnyItem>, private val mNewList: List<DataAnyItem>): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = mNewList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == mNewList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItemStory = oldList[oldItemPosition]
        val newItemStory = mNewList[newItemPosition]
        return oldItemStory.id == newItemStory.id
    }
}