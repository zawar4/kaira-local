package ai.kaira.app.Database

import ai.kaira.data.introduction.datasource.database.dao.UserDao
import ai.kaira.data.introduction.datasource.database.entity.UserEntity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(UserEntity::class), version = 1, exportSchema = false)
abstract class KairaDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: KairaDatabase? = null

        fun getDatabase(context: Context): KairaDatabase {

            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        KairaDatabase::class.java,
                        "kair_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}