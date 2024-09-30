package data.documentstore

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class Document(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    var title: String,
    var billAmount: String,
    var imageUri: String
)
