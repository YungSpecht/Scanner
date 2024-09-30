package com.example.scanner.ui.mainscreen

import data.documentstore.Document

data class MainScreenState(
    val documents: List<Document> = emptyList()
)
