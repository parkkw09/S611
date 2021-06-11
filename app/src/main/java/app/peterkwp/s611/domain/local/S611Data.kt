package app.peterkwp.s611.domain.local

import app.peterkwp.s611.domain.model.Book
import javax.inject.Inject

class S611Data @Inject constructor() {
//    val bookmark: HashMap<String, DetailBook> = HashMap()
    val bookmark: ArrayList<Book> = ArrayList()
    val history: ArrayList<String> = ArrayList()
}