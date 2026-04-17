package app.peter.s611.presentation.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peter.s611.domain.model.Book
import app.peter.s611.domain.usecase.NewBookUseCase
import app.peter.s611.application.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NewBookViewModel @Inject constructor(
    private val newBookUseCase: NewBookUseCase
) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private var _bookList: MutableLiveData<List<Book>> = MutableLiveData()
    val bookList: LiveData<List<Book>>
        get() = _bookList

    private var newBookPage = 1
    private var isNewBookLoading = false
    var isNewBookLastPage = false
        private set

    fun getNewBook(isRefresh: Boolean = false) {
        if (isNewBookLoading) return
        if (isRefresh) {
            newBookPage = 1
            isNewBookLastPage = false
        }
        if (isNewBookLastPage) return

        isNewBookLoading = true

        newBookUseCase.getNewBook(newBookPage.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { isNewBookLoading = false }
            .subscribe({ list ->
                if (list.isEmpty()) {
                    isNewBookLastPage = true
                } else {
                    val currentList = if (isRefresh) emptyList() else _bookList.value ?: emptyList()
                    _bookList.value = currentList + list
                    newBookPage++
                }
            }, { e ->
                Log.e(TAG, "getNewBook exception [${e.localizedMessage}]")
            }).also {
                disposable.add(it)
            }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "NewBookViewModel"
    }
}
