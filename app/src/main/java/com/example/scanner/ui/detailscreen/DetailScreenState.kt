package com.example.scanner.ui.detailscreen

import data.documentstore.Document

data class DetailScreenState(
    val document: Document? = null,
    val isTitleEditing: Boolean = false,
    val isBillAmountEditing: Boolean = false
)
