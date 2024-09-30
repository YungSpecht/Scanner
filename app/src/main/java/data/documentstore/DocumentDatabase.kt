package data.documentstore

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Document::class], version = 1, exportSchema = false)
abstract class DocumentDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
}