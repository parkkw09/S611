package app.peter.s611.data.entities

data class ListBook(
    val error: String = "",
    val total: String = "",
    val page: String = "1",
    val books: List<Book> = listOf()
)