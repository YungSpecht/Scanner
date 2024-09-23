package data

import java.net.URI

data class Document(
    var id: String,
    var title: String,
    var billAmount: String,
    var imageUri: URI
)
