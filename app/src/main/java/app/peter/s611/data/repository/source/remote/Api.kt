package app.peter.s611.data.repository.source.remote

import app.peter.s611.data.entities.OLEditionResponse
import app.peter.s611.data.entities.OLSearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search.json")
    fun getNewBooks(
        @Query("q") query: String = "programming",
        @Query("sort") sort: String = "new",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("fields") fields: String = "key,title,subtitle,isbn,cover_i,author_name,first_publish_year,number_of_pages_median,publisher,language"
    ): Single<OLSearchResponse>

    @GET("isbn/{isbn}.json")
    fun getBookDetail(@Path("isbn") isbn: String): Single<OLEditionResponse>

    @GET("search.json")
    fun getSearchBook(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("fields") fields: String = "key,title,subtitle,isbn,cover_i,author_name,first_publish_year,number_of_pages_median,publisher,language"
    ): Single<OLSearchResponse>
}