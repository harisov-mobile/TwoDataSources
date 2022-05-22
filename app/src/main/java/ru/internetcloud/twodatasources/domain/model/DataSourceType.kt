package ru.internetcloud.twodatasources.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class DataSourceType : Parcelable {
    ROOM_DATABASE1,
    ROOM_DATABASE2
    // FIREBASE
}
