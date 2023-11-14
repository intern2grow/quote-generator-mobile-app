package dev.awd.quotegen.di

import android.content.Context
import androidx.room.Room
import dev.awd.quotegen.data.local.QuoteDao
import dev.awd.quotegen.data.local.QuoteDb
import dev.awd.quotegen.data.remote.QuoteApi
import dev.awd.quotegen.data.repository.QuotesRepoImpl
import dev.awd.quotegen.domain.repository.QuotesRepository
import dev.awd.quotegen.domain.usecases.AddFavoriteQuoteUseCase
import dev.awd.quotegen.domain.usecases.GetFavoriteQuotesUseCase
import dev.awd.quotegen.domain.usecases.GetRandomQuotesUseCase
import dev.awd.quotegen.domain.usecases.RemoveFavoriteQuoteUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface AppModule {
    val quotesApi: QuoteApi
    val quoteDb: QuoteDb
    val quoteDao: QuoteDao
    val quotesRepository: QuotesRepository
    val getRandomQuotesUseCase: GetRandomQuotesUseCase
    val getFavoriteQuotesUseCase: GetFavoriteQuotesUseCase
    val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase
    val removeFavoriteQuoteUseCase: RemoveFavoriteQuoteUseCase
}

private const val BASE_URL = "https://api.quotable.io/"


class AppModuleImpl(
    context: Context
) : AppModule {

    override val quotesApi: QuoteApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }

    override val quoteDb: QuoteDb by lazy {
        Room.databaseBuilder(context, QuoteDb::class.java, "quotes-db").build()
    }
    override val quoteDao: QuoteDao by lazy {
        quoteDb.quoteDao
    }
    override val quotesRepository: QuotesRepository by lazy {
        QuotesRepoImpl(quotesApi, quoteDao)
    }
    override val getRandomQuotesUseCase: GetRandomQuotesUseCase by lazy {
        GetRandomQuotesUseCase(quotesRepository)
    }
    override val getFavoriteQuotesUseCase: GetFavoriteQuotesUseCase by lazy {
        GetFavoriteQuotesUseCase(quotesRepository)
    }
    override val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase by lazy {
        AddFavoriteQuoteUseCase(quotesRepository)
    }
    override val removeFavoriteQuoteUseCase: RemoveFavoriteQuoteUseCase by lazy {
        RemoveFavoriteQuoteUseCase(quotesRepository)
    }

}