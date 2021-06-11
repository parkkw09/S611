package app.peterkwp.s611.domain.usecase

import app.peterkwp.s611.domain.model.ListBook
import app.peterkwp.s611.domain.repository.LibraryRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchBookUseCase @Inject constructor(
    private val repository: LibraryRepository
) {

    fun searchBook(query: String, page: String): Single<ListBook> {
        return repository.getSearchBook(query, page)
    }

    fun addHistory(query: String) = repository.addHistory(query)
    fun getHistory() = repository.getHistory().toList()

    companion object {
        private const val TAG = "SearchBookUseCase"
    }
}