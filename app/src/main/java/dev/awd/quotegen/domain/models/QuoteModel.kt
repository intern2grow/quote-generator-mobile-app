package dev.awd.quotegen.domain.models

data class QuoteModel(
    val id: String?,
    val body: String?,
    val author: String?
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            body,
            author,
        )
        return matchingCombinations.any {
            it!!.contains(query, ignoreCase = true)
        }
    }
}