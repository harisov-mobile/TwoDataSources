package ru.internetcloud.twodatasources

import android.app.Application
import ru.internetcloud.twodatasources.di.DaggerApplicationComponent

class TwoDataSourcesApp : Application() {

    // помни про манифест: android:name=".TwoDataSourcesApp"

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}
