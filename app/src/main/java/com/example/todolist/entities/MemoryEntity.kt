package com.example.todolist.entities

import android.content.ContentValues
import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = MemoryEntity.TABLE_NAME)
data class MemoryEntity(
    @PrimaryKey
    @ColumnInfo(index = true, name = COLUMN_ID)
    var id: Long,
    @ColumnInfo(name = COLUMN_NAME)
    var name: String
)
{

    companion object {
        const val TABLE_NAME = "memories"
        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_NAME = "name";

        fun fromContentValues(values: ContentValues): MemoryEntity? {
            val menu: MemoryEntity =
                MemoryEntity(11289.toLong(),"name")
            if (values.containsKey(MemoryEntity.COLUMN_ID)) {
                menu.id =
                    values.getAsLong(MemoryEntity.COLUMN_ID)
            }
            if (values.containsKey(MemoryEntity.COLUMN_NAME)) {
                menu.name =
                    values.getAsString(MemoryEntity.COLUMN_NAME)
            }
            return menu
        }
    }
}