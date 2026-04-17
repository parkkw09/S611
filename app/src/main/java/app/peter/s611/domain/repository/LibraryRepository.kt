package app.peter.s611.domain.repository

import app.peter.s611.domain.model.Book
import app.peter.s611.domain.model.DetailBook
import app.peter.s611.domain.model.ListBook
import io.reactivex.rxjava3.core.Single

interface LibraryRepository {
    fun getNewBook(page: String = "1"): Single<ListBook>
    fun getDetailBook(isbn: String): Single<DetailBook>
    fun getSearchBook(query: String, page: String): Single<ListBook>
    fun addBookmark(book: Book)
    fun deleteBookmark(book: Book)
    fun checkBookmark(book: Book): Boolean
    fun updateBookmark(bookmark: List<Book>)
    fun getBookmark(): List<Book>
    fun addHistory(query: String)
    fun getHistory(): List<String>
}
