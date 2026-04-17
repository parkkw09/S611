package app.peter.s611.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peter.s611.domain.usecase.SearchBookUseCase
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val searchBookUseCase: SearchBookUseCase
) : ViewModel() {

    private var _history: MutableLiveData<List<String>> = MutableLiveData()
    val history: LiveData<List<String>>
        get() = _history

    fun getHistory() {
        _history.value = searchBookUseCase.getHistory()
    }

    companion object {
        private const val TAG = "HistoryViewModel"
    }
}
