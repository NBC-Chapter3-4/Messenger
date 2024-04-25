package com.nbc.messenger.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nbc.messenger.databinding.ItemRecyclerviewBinding
import com.nbc.messenger.databinding.ItemRecyclerviewReverseBinding
import com.nbc.messenger.databinding.LikedUserItemBinding
import com.nbc.messenger.model.User
import com.nbc.messenger.ui.main.viewholder.GridViewHolder
import com.nbc.messenger.ui.main.viewholder.ListViewHolder
import com.nbc.messenger.ui.main.viewholder.ReverseListViewHolder

enum class MyRecyclerViewLayout {
    GRID, LINEAR
}

class MyAdapter(
    private val likeClickListener: (position: Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<User> = emptyList()
    private var layoutMode = MyRecyclerViewLayout.LINEAR

    fun changeLayout(layout: MyRecyclerViewLayout) {
        layoutMode = layout
    }

    fun updateItems(users: List<User>) {
        items = users
        notifyDataSetChanged()
    }

    fun updateItems(
        users: (List<User>) -> List<User>
    ) {
        items = users(items)
        notifyDataSetChanged()
    }

    private var itemClickListener: MainRecyclerViewClickListener? = null

    fun registerItemClickListener(listener: MainRecyclerViewClickListener) {
        this.itemClickListener = listener
    }

    fun unregisterItemClickListener() {
        itemClickListener = null
    }

    override fun getItemViewType(position: Int): Int =
        when (layoutMode) {
            MyRecyclerViewLayout.GRID -> TYPE_GRID
            MyRecyclerViewLayout.LINEAR -> if (position % 2 == 0) EVEN else ODD
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_GRID -> {
                val binding = LikedUserItemBinding.inflate(layoutInflater, parent, false)
                GridViewHolder(binding)
            }

            EVEN -> {
                val binding = ItemRecyclerviewBinding.inflate(layoutInflater, parent, false)
                ListViewHolder(binding, likeClickListener)
            }

            ODD -> {
                val binding = ItemRecyclerviewReverseBinding.inflate(layoutInflater, parent, false)
                ReverseListViewHolder(binding, likeClickListener)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder.itemViewType) {
            TYPE_GRID -> (holder as GridViewHolder).bind(item)
            ODD -> (holder as ReverseListViewHolder).bind(item)
            EVEN -> (holder as ListViewHolder).bind(item)
        }
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(item)
        }
//        //추가
        holder.itemView.setOnLongClickListener {
            itemClickListener?.onItemLongClick(item)
            true
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    companion object {
        const val TYPE_GRID = 1
        const val EVEN = 2
        const val ODD = 3
    }
}