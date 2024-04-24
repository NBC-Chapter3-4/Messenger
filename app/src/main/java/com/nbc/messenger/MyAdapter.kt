package com.nbc.messenger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nbc.messenger.databinding.GridItemRecyclerviewBinding
import com.nbc.messenger.databinding.ItemRecyclerviewBinding
import com.nbc.messenger.databinding.ItemRecyclerviewReverseBinding
import com.nbc.messenger.model.ProfileImage
import com.nbc.messenger.model.User


class MyAdapter(
    private val item: List<User>,
    private val isGrid: Boolean,
    private val likeClickListener: (position: Int) ->Unit,
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    companion object {
        const val TYPE_GRID = 1
        const val EVEN = 2
        const val ODD = 3
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    private lateinit var itemClickListener: onItemClickListener
    fun setItemClickListener(listener: onItemClickListener){
        itemClickListener = listener
    }

    interface onTypeChangeClickListener{
        fun onTypeChangeClick(position: Int)
    }


    override fun getItemViewType(position: Int): Int {

        return if (!isGrid) {
            if (position % 2 == 0) EVEN else ODD
        } else {
            TYPE_GRID
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_GRID -> {
                val binding = GridItemRecyclerviewBinding.inflate(layoutInflater, parent, false)
                ViewHolder.GridViewHolder(binding)
            }

            EVEN -> {
                val binding = ItemRecyclerviewBinding.inflate(layoutInflater, parent, false)
                ViewHolder.ListViewHolder(binding, likeClickListener)
            }

            ODD -> {
                val binding = ItemRecyclerviewReverseBinding.inflate(layoutInflater, parent, false)
                ViewHolder.ReverseListViewHolder(binding, likeClickListener)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val item = item[position]
        when (holder) {
            is ViewHolder.ListViewHolder -> holder.bind(item)
            is ViewHolder.ReverseListViewHolder -> holder.bind(item)
            is ViewHolder.GridViewHolder -> holder.bind(item)
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

    sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        class ListViewHolder(
            private val binding: ItemRecyclerviewBinding,
            private val likeClickListener: (position: Int) -> Unit
        ) : ViewHolder(binding.root) {
            fun bind(item: User) {

                binding.ivLike.setOnClickListener {
                    likeClickListener(position)
                }
                Log.d("isLike", "${position},${item.isLike}")

                when (item.profileImage) {
                    is ProfileImage.ResourceImage -> {
                        binding.cvProfileImage.setImageResource(item.profileImage.id)
                    }

                    is ProfileImage.DefaultImage -> {
                        binding.cvProfileImage.setImageResource(R.drawable.ic_profile_default)
                    }
                }
                binding.tvUserName.text = item.name
                binding.ivLike.setImageResource(if (item.isLike) R.drawable.heart2 else R.drawable.heart)
            }
        }

        class ReverseListViewHolder(
            private val binding: ItemRecyclerviewReverseBinding,
            private val likeClickListener: (position: Int) -> Unit) :
            ViewHolder(binding.root) {
            fun bind(item: User) {
                binding.ivLike.setOnClickListener {
                    likeClickListener(position)
                }
                Log.d("isLike", "${position},${item.isLike}")
                when (item.profileImage) {
                    is ProfileImage.ResourceImage -> {
                        binding.cvProfileImage.setImageResource(item.profileImage.id)
                    }
                    is ProfileImage.DefaultImage -> {
                        binding.cvProfileImage.setImageResource(R.drawable.ic_profile_default)
                    }
                }
                binding.tvUserName.text = item.name
                binding.ivLike.setImageResource(if (item.isLike) R.drawable.heart2 else R.drawable.heart)
            }
        }

        class GridViewHolder(private val binding: GridItemRecyclerviewBinding) :
            ViewHolder(binding.root) {
            fun bind(item: User) {
            }
        }
    }
//    class ViewHolder(private val binding: ItemRecyclerviewBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: User) {
//
//            binding.tvUserName.text = item.name
//            binding.ivLike.setImageResource(if (item.isLike) R.drawable.ic_sample1 else R.drawable.ic_sample10)
//        }
//    }

}