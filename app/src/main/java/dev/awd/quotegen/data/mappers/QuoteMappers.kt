package dev.awd.quotegen.data.mappers

import dev.awd.quotegen.data.local.QuoteEntity
import dev.awd.quotegen.data.remote.Quote
import dev.awd.quotegen.domain.models.QuoteModel

fun Quote.toQuoteModel() = QuoteModel(
    id = id,
    body = content,
    author = author
)

fun QuoteEntity.toQuoteModel() = QuoteModel(
    id = id,
    body = content,
    author = author
)

fun QuoteModel.toQuoteEntity() = QuoteEntity(
    id = id ?: hashCode().toString(),
    content = body,
    author = author
)
