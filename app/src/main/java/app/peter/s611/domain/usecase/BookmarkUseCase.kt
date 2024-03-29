package app.peter.s611.domain.usecase

import app.peter.s611.data.entities.Book
import app.peter.s611.data.entities.DetailBook
import app.peter.s611.data.repository.LibraryRepository
import javax.inject.Inject

class BookmarkUseCase @Inject constructor(
    private val repository: LibraryRepository
) {

    fun addBookmark(detailBook: DetailBook) {
        repository.addBookmark(
            Book(detailBook.isbn13,
            detailBook.title,
            detailBook.subtitle,
            detailBook.price,
            detailBook.image,
            detailBook.url)
        )
    }

    fun deleteBookmark(detailBook: DetailBook) {
        repository.deleteBookmark(
            Book(detailBook.isbn13,
            detailBook.title,
            detailBook.subtitle,
            detailBook.price,
            detailBook.image,
            detailBook.url)
        )
    }

    fun checkBookmark(detailBook: DetailBook): Boolean {
        return repository.checkBookmark(
            Book(detailBook.isbn13,
            detailBook.title,
            detailBook.subtitle,
            detailBook.price,
            detailBook.image,
            detailBook.url)
        )
    }

    fun updateBookmark(bookmark: List<Book>) = repository.updateBookmark(bookmark)

    fun getBookmark(): List<Book> = repository.getBookmark()

        companion object {
        private const val TAG = "BookmarkUseCase"
    }
}