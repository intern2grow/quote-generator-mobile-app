package dev.awd.quotegen.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDao {

    @Query("SELECT * FROM QUOTE")
    suspend fun getAllQuotes(): List<QuoteEntity>

    @Query("SELECT * FROM QUOTE WHERE ID= :id ")
    fun getQuoteById(id: Int): QuoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quoteEntity: QuoteEntity)

    @Query("SELECT COUNT(*) FROM QUOTE ")
    suspend fun getQuotesCount(): Int

    @Delete
    suspend fun delete(quoteEntity: QuoteEntity)


}