package com.nbc.messenger.ui.main.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.nbc.messenger.R
import com.nbc.messenger.databinding.ItemRecyclerviewReverseBinding
import com.nbc.messenger.model.ProfileImage
import com.nbc.messenger.model.User

class ReverseListViewHolder(
    private val binding: ItemRecyclerviewReverseBinding,
    private val likeClickListener: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ivLike.setOnClickListener {
            likeClickListener(adapterPosition)
        }
    }

    fun bind(item: User) {
        when (item.profileImage) {
            is ProfileImage.ResourceImage -> {
                binding.cvProfileImage.setImageResource(item.profileImage.id)
            }

            is ProfileImage.DefaultImage -> {
                binding.cvProfileImage.setImageResource(R.drawable.ic_profile_default)
            }
        }
        binding.tvUserName.text = item.name
        binding.ivLike.setImageResource(if (item.isLike) R.drawable.heart2 else R.drawable.heart01)

        Log.d("item:", "$item")
    }
}