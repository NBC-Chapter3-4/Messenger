package com.nbc.messenger.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val name: String,
    val nickname: String,
    val phoneNumber: String,
    val email: String,
    val group: List<String>,
    val profileImage: ProfileImage,
    var isLike: Boolean,
    val isChecked: Boolean
): Parcelable