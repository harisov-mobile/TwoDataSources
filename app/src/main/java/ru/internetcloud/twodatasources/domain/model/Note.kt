package ru.internetcloud.twodatasources.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    var id: Int = 0,
    var name: String = "",
    var text: String = "",
    var idFirebase: String = ""
) : Parcelable
