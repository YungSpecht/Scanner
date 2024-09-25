package com.example.scanner.ui.detailscreen

import data.Document

data class DetailScreenState(
    val document: Document,
    val isTitleEditing: Boolean = false,
    val isBillAmountEditing: Boolean = false
)
