package app.peter.s611.data.repository.source.local

import app.peter.s611.domain.model.Book
import javax.inject.Inject

class S611Data @Inject constructor() {
//    val bookmark: HashMap<String, DetailBook> = HashMap()
    val bookmark: ArrayList<Book> = ArrayList()
    val history: ArrayList<String> = ArrayList()
}