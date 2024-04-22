package com.nbc.messenger.model

import androidx.annotation.DrawableRes

sealed interface ProfileImage {

    data class ResourceImage(
        @DrawableRes val id: Int,
    ) : ProfileImage

    data object DefaultImage : ProfileImage

}