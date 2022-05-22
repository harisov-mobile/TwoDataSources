package ru.internetcloud.twodatasources.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class OperationMode : Parcelable {
    ADD,
    EDIT
}
