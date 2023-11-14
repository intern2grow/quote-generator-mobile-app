package dev.awd.quotegen.data.remote

import com.google.gson.annotations.SerializedName

data class QuotesResponse(

    var quotes: List<Quote>? = null,
)

data class Quote(
    @SerializedName("_id") var id: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("tags") var tags: ArrayList<String> = arrayListOf(),
    @SerializedName("authorSlug") var authorSlug: String? = null,
    @SerializedName("length") var length: Int? = null,
    @SerializedName("dateAdded") var dateAdded: String? = null,
    @SerializedName("dateModified") var dateModified: String? = null
)
