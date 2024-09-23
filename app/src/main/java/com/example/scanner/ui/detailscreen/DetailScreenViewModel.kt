package com.example.scanner.ui.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.DocumentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailScreenState())
    val uiState = _uiState.asStateFlow()

    fun loadDocument(documentId: String) {
        viewModelScope.launch {
            val document = documentRepository.getDocumentById(documentId)
            document?.let {
                _uiState.value = DetailScreenState(document = it)
            }
        }
    }

    fun toggleTitleEdit() {
        _uiState.value = _uiState.value.copy(
            isTitleEditing = !(_uiState.value.isTitleEditing ?: false)
        )
    }

    fun toggleBillAmountEdit() {
        _uiState.value = _uiState.value.copy(
            isBillAmountEditing = !(_uiState.value.isBillAmountEditing ?: false)
        )
    }

    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.let { uiState ->
            val updatedDocument = uiState.document?.copy(title = newTitle)
            viewModelScope.launch {
                if (updatedDocument != null) {
                    documentRepository.updateDocument(updatedDocument)
                }
            }
            uiState.copy(document = updatedDocument, isTitleEditing = false)
        }
    }

    fun updateBillAmount(newAmount: String) {
        val amount = newAmount.toDoubleOrNull() ?: return
        _uiState.value = _uiState.value.let { uiState ->
            val updatedDocument = uiState.document?.copy(billAmount = amount)
            viewModelScope.launch {
                documentRepository.updateDocument(updatedDocument)
            }
            uiState.copy(document = updatedDocument, isBillAmountEditing = false)
        }!!
    }

}