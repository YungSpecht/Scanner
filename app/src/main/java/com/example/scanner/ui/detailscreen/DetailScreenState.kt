package com.example.scanner.ui.detailscreen

import data.documentstore.Document

data class DetailScreenState(
    val document: Document = Document(title = "", billAmount = "", imageUri = ""),
    val isTitleEditing: Boolean = false,
    val isBillAmountEditing: Boolean = false,
    val showDialog: Boolean = false
)
