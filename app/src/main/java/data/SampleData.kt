package data

object SampleData {
    private var docList = mutableListOf<Document>(
        Document(1, "Rechnung #1", "3000€"),
        Document(2, "Rechnung #2", "5000€"),
        Document(3, "Rechnung #3", "300€"),
        Document(4, "Rechnung #4", "3650€"),
        Document(5, "Rechnung #5", "78743€")
    )

    fun getSampleData(): List<Document> {
        return docList
    }
}