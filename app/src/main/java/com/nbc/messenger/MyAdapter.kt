package com.nbc.messenger

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.nbc.messenger.databinding.ItemRecyclerviewBinding
import com.nbc.messenger.databinding.ItemRecyclerviewRevesceBinding
import com.nbc.messenger.databinding.LikedUserItemBinding
import com.nbc.messenger.model.ProfileImage
import com.nbc.messenger.model.User

class MyAdapter(
    private val item: List<User>,
    private val isGrid: Boolean,
    private val likeClickListener: (position: Int) -> Unit,
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private val TAG = "Like"


    companion object {
        const val TYPE_GRID = 1
        const val EVEN = 2
        const val ODD = 3
    }

    interface onItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }
    private lateinit var itemClickListener: onItemClickListener

    fun setItemClickListener(listener: onItemClickListener) {
        this.itemClickListener = listener
    }

//    fun bindLongClick(holder: ViewHolder, position: Int){
//        holder.itemView.setOnClickListener{
//            itemClickListener.onItemClick(position)
//        }
//        holder.itemView.setOnLongClickListener {
//            itemClickListener.onItemLongClick(position)
//            true
//        }
//    }


//    interface onItemLongClickListener{
//        fun onItemLongClick(position: Int)
//    }
//    var onItemLongClickListener: OnItemLongClickListener ?= null
//
//    fun setOnTimeLongClickListener(listener: OnItemLongClickListener){
//        this.onItemLongClickListener = listener
//    }



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
                val binding = LikedUserItemBinding.inflate(layoutInflater, parent, false)
                ViewHolder.GridViewHolder(binding)
            }

            EVEN -> {
                val binding = ItemRecyclerviewBinding.inflate(layoutInflater, parent, false)
                ViewHolder.ListViewHolder(binding, likeClickListener)
            }

            ODD -> {
                val binding = ItemRecyclerviewRevesceBinding.inflate(layoutInflater, parent, false)
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
//        //추가
        holder.itemView.setOnLongClickListener {
            itemClickListener.onItemLongClick(position)
            true
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun getItem(position:Int):User{
        return item[position]
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
            private val binding: ItemRecyclerviewRevesceBinding,
            private val likeClickListener: (position: Int) -> Unit
        ) :
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

        class GridViewHolder(private val binding: LikedUserItemBinding) :
            ViewHolder(binding.root) {
            fun bind(item: User) {
                when (item.profileImage) {
                    is ProfileImage.ResourceImage -> {
                        binding.profileImageView.setImageResource(item.profileImage.id)
                    }

                    is ProfileImage.DefaultImage -> {
                        binding.profileImageView.setImageResource(R.drawable.ic_profile_default)
                    }
                }
                binding.userNameTextView.text = item.name

                when(item.isChecked){
                    false -> binding.redNum1.isVisible = true
                    true -> binding.redNum1.isVisible = false
                }

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