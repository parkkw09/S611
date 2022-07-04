package app.peter.s611.domain.usecase

import app.peter.s611.data.entities.Book
import app.peter.s611.data.repository.LibraryRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NewBookUseCase @Inject constructor(
    private val repository: LibraryRepository
) {

    fun getNewBook(): Single<List<Book>> {
        return repository.getNewBook().map {
            it.books
        }
    }

    companion object {
        private const val TAG = "NewBookUseCase"
    }
}