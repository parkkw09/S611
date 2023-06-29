package app.peter.s611.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peter.s611.data.entities.Book
import app.peter.s611.data.entities.DetailBook
import app.peter.s611.domain.usecase.BookmarkUseCase
import app.peter.s611.domain.usecase.DetailBookUseCase
import app.peter.s611.domain.usecase.NewBookUseCase
import app.peter.s611.domain.usecase.SearchBookUseCase
import app.peter.s611.application.Log
import app.peter.s611.application.Utils
import com.google.android.gms.appset.AppSet
import com.google.android.gms.appset.AppSetIdClient
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor (
    private val newBookUseCase: NewBookUseCase,
    private val bookmarkUseCase: BookmarkUseCase,
    private val detailBookUseCase: DetailBookUseCase,
    private val searchBookUseCase: SearchBookUseCase
) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private var _bookList: MutableLiveData<List<Book>> = MutableLiveData()
    val bookList: LiveData<List<Book>>
        get() = _bookList

    private var _bookmark: MutableLiveData<List<Book>> = MutableLiveData()
    val bookmark: LiveData<List<Book>>
        get() = _bookmark

    private var _searchBookList: MutableLiveData<List<Book>> = MutableLiveData()
    val searchBookList: LiveData<List<Book>>
        get() = _searchBookList

    private var _history: MutableLiveData<List<String>> = MutableLiveData()
    val history: LiveData<List<String>>
        get() = _history

    private var _currentSearchQuery: MutableLiveData<String> = MutableLiveData()
    val currentSearchQuery: LiveData<String>
        get() = _currentSearchQuery

    var appName: String? = null
    var reviewManager: ReviewManager? = null
    var client: AppSetIdClient? = null

    fun initClient(context: Context, appName: String) {
        client = AppSet.getClient(context)
        reviewManager = ReviewManagerFactory.create(context)
        this.appName = Utils.convertAppName(context, appName)
    }

    fun getNewBook() {
        newBookUseCase.getNewBook()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                _bookList.value = list
            }, { e ->
                Log.e(TAG, "getNewBook exception [${e.localizedMessage}]")
            }).apply {
                disposable.add(this)
            }
    }

    fun getDetailBook(isbn: String, func: (information: DetailBook) -> Unit) {
        detailBookUseCase.getDetailBook(isbn)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ book ->
                func.invoke(book)
            }, { e ->
                Log.e(TAG, "getDetailBook exception [${e.localizedMessage}]")
            }).apply {
                disposable.add(this)
            }
    }

    fun addBookmark(book: DetailBook) = bookmarkUseCase.addBookmark(book)

    fun deleteBookmark(book: DetailBook) = bookmarkUseCase.deleteBookmark(book)

    fun checkBookmark(book: DetailBook): Boolean = bookmarkUseCase.checkBookmark(book)

    fun updateBookmark(bookmark: List<Book>) = bookmarkUseCase.updateBookmark(bookmark)

    fun getBookmark() {
        _bookmark.value = bookmarkUseCase.getBookmark()
    }

    fun searchBook(query: String, page: String = "1", func: (Int, Int) -> Unit) {
        searchBookUseCase.searchBook(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                val searchList = result.books
                val pageCount = result.page.toInt()
                val totalCount = result.total.toInt()
                func.invoke(pageCount, totalCount)
                _searchBookList.value = searchList
            }, { e ->
                Log.e(TAG, "searchBook exception [${e.localizedMessage}]")
            }).apply {
                disposable.add(this)
            }
    }

    fun clearSearchResult() {
        _searchBookList.value = listOf()
    }

    fun setCurrentSearchQuery(query: String) {
        _currentSearchQuery.value = query
    }

    fun addHistory(query: String) = searchBookUseCase.addHistory(query)

    fun getHistory() {
        _history.value = searchBookUseCase.getHistory()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}