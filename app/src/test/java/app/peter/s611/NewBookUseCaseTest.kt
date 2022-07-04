package app.peter.s611

import app.peter.s611.data.repository.source.local.S611Data
import app.peter.s611.data.entities.Book
import app.peter.s611.data.entities.ListBook
import app.peter.s611.data.repository.source.remote.Api
import app.peter.s611.data.repository.LibraryRepository
import app.peter.s611.domain.usecase.NewBookUseCase
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals

class NewBookUseCaseTest {

    private val mockServer = MockWebServer()
    lateinit var repository: LibraryRepository
    private val disposable: CompositeDisposable = CompositeDisposable()

    @Before
    fun setup() {
        mockServer.start()
        val api = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(Api::class.java)
        val data = S611Data()
        repository = LibraryRepository(api, data)
    }

    @After
    fun shutdown() {
        disposable.clear()
        mockServer.shutdown()
    }

    @Test
    fun `새로운 책 읽어오기 테스트 - 정상적으로 데이터를 읽어와야 한다`() {
        val useCase = NewBookUseCase(repository)
        mockServer.enqueue(MockResponse().setBody(Gson().toJson(RESPONSE)))
        useCase.getNewBook()
            .subscribeOn(Schedulers.io())
            .subscribe({ list ->
                assertEquals(listOf(ITEM_BOOK), list)
            },{
            }).apply {
                disposable.add(this)
            }
    }

    companion object {
        private val ITEM_BOOK = Book(isbn = "1001621860589",
            title = "Python Notes for Professionals",
            subtitle = "",
            price = "\$0.00",
            image = "https://itbook.store/img/books/1001621860589.png",
            url = "https://itbook.store/books/1001621860589")
        private val RESPONSE = ListBook(error = "0", total = "20", books = listOf(ITEM_BOOK))
    }
}