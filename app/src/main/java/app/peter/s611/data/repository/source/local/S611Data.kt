package app.peter.s611.data.repository.source.local

import app.peter.s611.data.entities.Book
import javax.inject.Inject

class S611Data @Inject constructor() {
//    val bookmark: HashMap<String, DetailBook> = HashMap()
    val bookmark: ArrayList<Book> = ArrayList()
    val history: ArrayList<String> = ArrayList()
}