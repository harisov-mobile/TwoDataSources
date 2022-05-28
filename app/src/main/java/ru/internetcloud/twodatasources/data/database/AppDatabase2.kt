package ru.internetcloud.twodatasources.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.internetcloud.twodatasources.data.entity.NoteDbModel

@Database(entities = [NoteDbModel::class], version = 1, exportSchema = true)
abstract class AppDatabase2 : RoomDatabase() {

    abstract fun appDao2(): AppDao2

    companion object {

        private const val DATABASE_NAME = "notes2.db"

        @Volatile // чтобы данная переменная не кэшировалась!!!
        private var instance: AppDatabase2? = null

        private val Lock = Any()

        fun getInstance(application: Application): AppDatabase2 {
            instance?.let {
                return it
            }
            synchronized(Lock) {
                instance?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase2::class.java,
                    DATABASE_NAME
                )
                    .build()
                instance = db
                return db
            }
        }
    }
}
