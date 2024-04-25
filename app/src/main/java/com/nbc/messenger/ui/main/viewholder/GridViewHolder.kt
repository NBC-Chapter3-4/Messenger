package com.nbc.messenger.ui.main.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.nbc.messenger.R
import com.nbc.messenger.databinding.LikedUserItemBinding
import com.nbc.messenger.model.ProfileImage
import com.nbc.messenger.model.User

class GridViewHolder(
    private val binding: LikedUserItemBinding
) : RecyclerView.ViewHolder(binding.root) {
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

        when (item.isChecked) {
            false -> binding.redNum1.isVisible = true
            true -> binding.redNum1.isVisible = false
        }

    }
}