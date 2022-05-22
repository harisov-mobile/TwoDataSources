package ru.internetcloud.twodatasources.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.internetcloud.twodatasources.data.entity.NoteDbModel

@Database(entities = [NoteDbModel::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {

        private const val DATABASE_NAME = "notes.db"

        @Volatile // чтобы данная переменная не кэшировалась!!!
        private var instance: AppDatabase? = null

        private val Lock = Any()

        fun getInstance(application: Application): AppDatabase {
            instance?.let {
                return it
            }
            synchronized(Lock) {
                instance?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                instance = db
                return db
            }
        }
    }
}
