package data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DocumentRepository @Inject constructor(
    private val documentDao: DocumentDao
) {

    suspend fun getDocuments(): List<Document> {
        return documentDao.getDocuments()
    }

    suspend fun addDocument(document: Document) {
        documentDao.insertDocument(document)
    }

    suspend fun updateDocument(document: Document) {
        documentDao.updateDocument(document)
    }

    suspend fun deleteDocument(document: Document) {
        documentDao.deleteDocument(document)
    }

    suspend fun getDocumentById(id: Long): Document? {
        return documentDao.getDocumentById(id)
    }
}