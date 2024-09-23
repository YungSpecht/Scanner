package data

import java.net.URI

class InMemoryDocumentRepository : DocumentRepository{
    private val documents = mutableListOf<Document>(
        Document("1", "Rechnung #1", "3000€", URI("https://www.google.com")),
        Document("2", "Rechnung #2", "5000€", URI("https://www.google.com")),
        Document("3", "Rechnung #3", "300€", URI("https://www.google.com")),
        Document("4", "Rechnung #4", "3650€", URI("https://www.google.com")),
        Document("5", "Rechnung #5", "78743€", URI("https://www.google.com"))
    )


    override suspend fun addDocument(document: Document) {
        documents.add(document)
    }

    override suspend fun getDocuments(): List<Document> {
        return documents.toList()
    }

    override suspend fun getDocumentById(id: String): Document? {
        return documents.find { it.id == id }
    }

    override suspend fun updateDocument(document: Document) {
        val index = documents.indexOfFirst { it.id == document.id }
        if (index != -1) {
            documents[index] = document
        }
    }
}