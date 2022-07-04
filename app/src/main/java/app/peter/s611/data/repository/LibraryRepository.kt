package app.peter.s611.data.repository

import app.peter.s611.data.repository.source.local.S611Data
import app.peter.s611.data.entities.Book
import app.peter.s611.data.repository.source.remote.Api
import javax.inject.Inject

class LibraryRepository @Inject constructor (
    private val api: Api,
    private val data: S611Data
) {

    fun getNewBook() = api.getNewBooks()
    fun getDetailBook(isbn: String) = api.getBookDetail(isbn)
    fun getSearchBook(query: String, page: String) = api.getSearchBook(query, page)

    fun addBookmark(book: Book) {
        if (data.bookmark.contains(book)) return
        data.bookmark.add(book)
    }

    fun deleteBookmark(book: Book) {
        data.bookmark.remove(book)
    }

    fun checkBookmark(book: Book): Boolean = data.bookmark.contains(book)

    fun updateBookmark(bookmark: List<Book>) {
        data.bookmark.clear()
        data.bookmark.addAll(bookmark)
    }

    fun getBookmark() = data.bookmark

    fun addHistory(query: String) {
        if (data.history.contains(query)) return
        data.history.add(query)
    }

    fun getHistory() = data.history
}