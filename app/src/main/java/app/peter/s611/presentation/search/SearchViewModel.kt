package app.peter.s611.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peter.s611.domain.model.Book
import app.peter.s611.domain.usecase.SearchBookUseCase
import app.peter.s611.application.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchBookUseCase: SearchBookUseCase
) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private var _searchBookList: MutableLiveData<List<Book>> = MutableLiveData()
    val searchBookList: LiveData<List<Book>>
        get() = _searchBookList

    private var _history: MutableLiveData<List<String>> = MutableLiveData()
    val history: LiveData<List<String>>
        get() = _history

    private var _currentSearchQuery: MutableLiveData<String> = MutableLiveData()
    val currentSearchQuery: LiveData<String>
        get() = _currentSearchQuery

    // Search pagination state
    private var searchPageCount = 1
    private var isSearchLoading = false
    private var isSearchComplete = false
    private val accumulatedSearchBooks: MutableList<Book> = mutableListOf()

    fun resetSearchState() {
        searchPageCount = 1
        isSearchLoading = false
        isSearchComplete = false
        accumulatedSearchBooks.clear()
    }

    fun searchBook(query: String) {
        if (isSearchLoading || isSearchComplete) return
        isSearchLoading = true

        val isFirstPage = searchPageCount == 1
        searchBookUseCase.searchBook(query, searchPageCount.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                val newBooks = result.books
                val totalCount = result.total.toIntOrNull() ?: 0

                if (isFirstPage) {
                    accumulatedSearchBooks.clear()
                }
                accumulatedSearchBooks.addAll(newBooks)

                if (accumulatedSearchBooks.size >= totalCount && searchPageCount != 1) {
                    isSearchComplete = true
                } else {
                    searchPageCount++
                }
                isSearchLoading = false
                _searchBookList.value = accumulatedSearchBooks.toList()
            }, { e ->
                isSearchLoading = false
                Log.e(TAG, "searchBook exception [${e.localizedMessage}]")
            }).also {
                disposable.add(it)
            }
    }

    fun clearSearchResult() {
        accumulatedSearchBooks.clear()
        _searchBookList.value = listOf()
    }

    fun setCurrentSearchQuery(query: String) {
        _currentSearchQuery.value = query
    }

    fun addHistory(query: String) = searchBookUseCase.addHistory(query)

    fun getHistory() {
        _history.value = searchBookUseCase.getHistory()
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}
