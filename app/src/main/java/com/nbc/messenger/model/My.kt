package com.nbc.messenger.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class My(
    val id: String,
    val name: String,
    val nickname: String,
    val phoneNumber: String,
    val email: String,
    val profileImage: ProfileImage
):Parcelable