package app.peter.s611.data.repository

import app.peter.s611.data.repository.source.local.S611Data
import app.peter.s611.data.repository.source.remote.Api
import app.peter.s611.data.repository.source.remote.OLResponseMapper
import app.peter.s611.domain.model.Book
import app.peter.s611.domain.model.DetailBook
import app.peter.s611.domain.model.ListBook
import app.peter.s611.domain.repository.LibraryRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val api: Api,
    private val data: S611Data
) : LibraryRepository {

    override fun getNewBook(page: String): Single<ListBook> =
        api.getNewBooks(page = page.toIntOrNull() ?: 1).map { OLResponseMapper.toListBook(it, page) }

    override fun getDetailBook(isbn: String): Single<DetailBook> =
        api.getBookDetail(isbn).map { OLResponseMapper.toDetailBook(it, isbn) }

    override fun getSearchBook(query: String, page: String): Single<ListBook> =
        api.getSearchBook(query, page.toIntOrNull() ?: 1).map { OLResponseMapper.toListBook(it, page) }

    override fun addBookmark(book: Book) {
        if (data.bookmark.contains(book)) return
        data.bookmark.add(book)
    }

    override fun deleteBookmark(book: Book) {
        data.bookmark.remove(book)
    }

    override fun checkBookmark(book: Book): Boolean = data.bookmark.contains(book)

    override fun updateBookmark(bookmark: List<Book>) {
        data.bookmark.clear()
        data.bookmark.addAll(bookmark)
    }

    override fun getBookmark(): List<Book> = data.bookmark

    override fun addHistory(query: String) {
        if (data.history.contains(query)) return
        data.history.add(query)
    }

    override fun getHistory(): List<String> = data.history
}
