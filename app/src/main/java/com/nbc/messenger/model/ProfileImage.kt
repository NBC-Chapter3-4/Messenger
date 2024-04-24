package com.nbc.messenger.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface ProfileImage: Parcelable {
    @Parcelize
    data class ResourceImage(
        @DrawableRes val id: Int,
    ) : ProfileImage
    @Parcelize
    data object DefaultImage : ProfileImage

}