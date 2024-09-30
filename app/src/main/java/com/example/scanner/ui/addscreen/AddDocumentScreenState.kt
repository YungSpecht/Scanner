package com.example.scanner.ui.addscreen

data class AddDocumentScreenState(
    var title: String = "",
    var billAmount: String = "",
    var recognizedText: String = "No text found",
    var isLoading: Boolean = false
)
