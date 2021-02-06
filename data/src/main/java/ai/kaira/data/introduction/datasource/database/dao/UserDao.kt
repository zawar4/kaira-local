package ai.kaira.data.introduction.datasource.database.dao

import ai.kaira.data.introduction.datasource.database.entity.UserEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity:UserEntity)
}