package app.peter.s611.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peter.s611.domain.model.Book
import app.peter.s611.domain.usecase.BookmarkUseCase
import javax.inject.Inject

class BookmarkViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {

    private var _bookmark: MutableLiveData<List<Book>> = MutableLiveData()
    val bookmark: LiveData<List<Book>>
        get() = _bookmark

    fun getBookmark() {
        _bookmark.value = bookmarkUseCase.getBookmark()
    }

    fun updateBookmark(bookmark: List<Book>) = bookmarkUseCase.updateBookmark(bookmark)

    companion object {
        private const val TAG = "BookmarkViewModel"
    }
}
