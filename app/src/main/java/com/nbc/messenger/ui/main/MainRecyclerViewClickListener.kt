package com.nbc.messenger.ui.main

import com.nbc.messenger.model.User

interface MainRecyclerViewClickListener {
    fun onItemClick(user: User)
    fun onItemLongClick(user: User)
}