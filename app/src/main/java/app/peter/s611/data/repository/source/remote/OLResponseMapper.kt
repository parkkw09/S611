package app.peter.s611.data.repository.source.remote

import app.peter.s611.application.DomainConst
import app.peter.s611.domain.model.Book
import app.peter.s611.domain.model.DetailBook
import app.peter.s611.domain.model.ListBook
import app.peter.s611.data.entities.OLEditionResponse
import app.peter.s611.data.entities.OLSearchDoc
import app.peter.s611.data.entities.OLSearchResponse

/**
 * Open Library API 응답을 Domain 모델(ListBook, DetailBook, Book)로 변환하는 매퍼.
 */
object OLResponseMapper {

    fun toListBook(response: OLSearchResponse, page: String = "1"): ListBook {
        val books = response.docs
            .filter { doc -> !doc.isbn.isNullOrEmpty() }
            .map { doc -> toBook(doc) }
        return ListBook(
            error = "0",
            total = response.numFound.toString(),
            page = page,
            books = books
        )
    }

    fun toBook(doc: OLSearchDoc): Book {
        val isbn = doc.isbn?.firstOrNull() ?: ""
        val coverUrl = doc.coverId?.let {
            "${DomainConst.COVER_URL}${it}-L.jpg"
        } ?: ""
        val bookUrl = if (doc.key.isNotEmpty()) {
            "${DomainConst.URL}${doc.key.trimStart('/')}"
        } else ""

        return Book(
            isbn = isbn,
            title = doc.title,
            subtitle = doc.subtitle ?: "",
            price = "",
            image = coverUrl,
            url = bookUrl
        )
    }

    fun toDetailBook(response: OLEditionResponse, isbn: String): DetailBook {
        val coverUrl = response.covers?.firstOrNull()?.let {
            "${DomainConst.COVER_URL}${it}-L.jpg"
        } ?: ""
        val isbn13 = response.isbn13?.firstOrNull() ?: isbn
        val isbn10 = response.isbn10?.firstOrNull() ?: ""
        val language = response.languages?.firstOrNull()?.key?.removePrefix("/languages/") ?: ""
        val publisher = response.publishers?.firstOrNull() ?: ""
        val authors = response.authors?.joinToString(", ") { it.key.removePrefix("/authors/") } ?: ""
        val year = extractYear(response.publishDate)
        val description = extractDescription(response.description)
        val bookUrl = response.works?.firstOrNull()?.key?.let {
            "${DomainConst.URL}${it.trimStart('/')}"
        } ?: ""

        return DetailBook(
            error = "0",
            title = response.title,
            subtitle = response.subtitle ?: "",
            authors = authors,
            publisher = publisher,
            language = language,
            isbn10 = isbn10,
            isbn13 = isbn13,
            pages = (response.numberOfPages ?: 0).toString(),
            year = year,
            rating = "0",
            desc = description,
            price = "",
            image = coverUrl,
            url = bookUrl,
            pdfFreeBook = ""
        )
    }

    private fun extractYear(publishDate: String?): String {
        if (publishDate == null) return ""
        val yearRegex = Regex("\\d{4}")
        return yearRegex.find(publishDate)?.value ?: publishDate
    }

    private fun extractDescription(desc: Any?): String {
        return when (desc) {
            is String -> desc
            is Map<*, *> -> desc["value"]?.toString() ?: ""
            else -> ""
        }
    }
}
