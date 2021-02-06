package ai.kaira.data.introduction.datasource.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
        @ColumnInfo(name = "id") val id:String,
        @ColumnInfo(name = "first_name") val firstName:String,
        @ColumnInfo(name="language") val language:String,
        @ColumnInfo(name="created_at") val createdAt:String,
        @ColumnInfo(name="verified") val verified:Boolean,
        @ColumnInfo(name="valid_group_code") val validGroupCode:Boolean,
        @PrimaryKey @ColumnInfo(name = "unique") val row : Int = 1
)
