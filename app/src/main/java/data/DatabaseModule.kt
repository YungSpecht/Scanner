package data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DocumentDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DocumentDatabase::class.java,
            "document_database"
        ).build()
    }

    @Provides
    fun provideDocumentDao(database: DocumentDatabase): DocumentDao {
        return database.documentDao()
    }

    @Provides
    fun provideDocumentRepository(documentDao: DocumentDao): DocumentRepository {
        return DocumentRepository(documentDao)
    }

}