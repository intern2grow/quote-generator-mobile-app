package dev.awd.quotegen.data.local

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QUOTE")
data class QuoteEntity(
    @ColumnInfo("ID")
    @PrimaryKey
    @NonNull
    var id: String ,
    @ColumnInfo("CONTENT")
    var content: String? = null,
    @ColumnInfo("AUTHOR")
    var author: String? = null,
)
