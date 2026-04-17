package app.peter.s611.presentation.detail

import androidx.lifecycle.ViewModel
import app.peter.s611.domain.model.DetailBook
import app.peter.s611.domain.usecase.BookmarkUseCase
import app.peter.s611.domain.usecase.DetailBookUseCase
import app.peter.s611.application.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val detailBookUseCase: DetailBookUseCase,
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun getDetailBook(isbn: String, func: (information: DetailBook) -> Unit) {
        detailBookUseCase.getDetailBook(isbn)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ book ->
                func.invoke(book)
            }, { e ->
                Log.e(TAG, "getDetailBook exception [${e.localizedMessage}]")
            }).also {
                disposable.add(it)
            }
    }

    fun addBookmark(book: DetailBook) = bookmarkUseCase.addBookmark(book)

    fun deleteBookmark(book: DetailBook) = bookmarkUseCase.deleteBookmark(book)

    fun checkBookmark(book: DetailBook): Boolean = bookmarkUseCase.checkBookmark(book)

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}
