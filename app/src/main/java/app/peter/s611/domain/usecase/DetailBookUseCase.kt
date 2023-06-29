package app.peter.s611.domain.usecase

import app.peter.s611.data.entities.DetailBook
import app.peter.s611.data.repository.LibraryRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DetailBookUseCase @Inject constructor(
    private val repository: LibraryRepository
) {

    fun getDetailBook(isbn: String): Single<DetailBook> {
        return repository.getDetailBook(isbn)
    }

    companion object {
        private const val TAG = "DetailBookUseCase"
    }
}