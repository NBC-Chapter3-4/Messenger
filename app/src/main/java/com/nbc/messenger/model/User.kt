package com.nbc.messenger.model

data class User(
    val id: String,
    val name: String,
    val nickname: String,
    val phoneNumber: String,
    val email: String,
    val group: List<String>,
    val profileImage: ProfileImage,
    val isLike: Boolean,
    val isChecked: Boolean
)