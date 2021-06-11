package app.peterkwp.s611.domain.remote

import app.peterkwp.s611.domain.data.DomainConst
import app.peterkwp.s611.domain.model.DetailBook
import app.peterkwp.s611.domain.model.ListBook
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