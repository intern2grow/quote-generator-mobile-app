package dev.awd.quotegen.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([QuoteEntity::class], version = 1, exportSchema = false)
abstract class QuoteDb : RoomDatabase() {

    abstract val quoteDao: QuoteDao
}