package app.peter.s611.data.repository.source.remote

import app.peter.s611.application.DomainConst
import app.peter.s611.data.entities.DetailBook
import app.peter.s611.data.entities.ListBook
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET(DomainConst.VERSION + "/new")
    fun getNewBooks(): Single<ListBook>

    @GET(DomainConst.VERSION + "/books/{isbn}")
    fun getBookDetail(@Path("isbn") isbn: String): Single<DetailBook>

    @GET(DomainConst.VERSION + "/search/{query}/{page}")
    fun getSearchBook(@Path("query") query: String, @Path("page") page: String = "1"): Single<ListBook>
}