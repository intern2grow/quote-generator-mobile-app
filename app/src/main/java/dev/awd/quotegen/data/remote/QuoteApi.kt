package dev.awd.quotegen.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    @GET("quotes/random")
    suspend fun getRandomQuotes(@Query("limit") limit: Int = 20): List<Quote>

    @GET("/quotes/:id")
    suspend fun getQuoteById(id: String)
}