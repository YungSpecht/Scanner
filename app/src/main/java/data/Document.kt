package data

import java.net.URI

data class Document(
    var id: Int,
    var title: String,
    var billAmount: String,
    var imageUri: URI
)
